package com.devtalk.payment.paymentservice.application;

import com.devtalk.payment.global.code.ErrorCode;
import com.devtalk.payment.global.error.exception.NotFoundException;
import com.devtalk.payment.paymentservice.application.port.in.ConsultationUseCase;
import com.devtalk.payment.paymentservice.application.port.in.EmailUseCase;
import com.devtalk.payment.paymentservice.application.port.in.PaymentUseCase;
import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentRepo;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import com.devtalk.payment.paymentservice.domain.payment.PaymentStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.devtalk.payment.paymentservice.application.port.in.dto.PaymentReq.*;
import static com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {
    private final PaymentRepo paymentRepo;
    private final ConsultationUseCase consultationUseCase;
    private final EmailUseCase emailUseCase;
    private final PaymentProperty paymentProperty;

    // 결제 토큰 생성 (포트원 API사용을 위해 필요함)
    @Override
    public String getToken() {
        String impKey = paymentProperty.getImpKey();
        String impSecret = paymentProperty.getImpSecret();

        Map<String, Object> params = new HashMap<>();
        params.put("imp_key", impKey);
        params.put("imp_secret", impSecret);

        WebClient wc = WebClient.create("https://api.iamport.kr/users/getToken");
        String response = wc.post()
                .bodyValue(params)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            // ObjectMapper를 사용하여 JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(response);
            String accessToken = node.get("response").get("access_token").asText();
            return accessToken;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getPaymentLink(String token, Long consultationId) {
        Map<String, Object> paymentInfo = new HashMap<>();
        paymentInfo.put("title", "테스트가맹점");
        paymentInfo.put("user_code", "imp67671220");
        paymentInfo.put("amount", 10000);
        paymentInfo.put("merchant_uid", "merchant_" + System.currentTimeMillis());  // unique merchant_uid
        paymentInfo.put("name", "결제링크 테스트");
        paymentInfo.put("tax_free", "면세공급가액");
        paymentInfo.put("currency", "KRW");
        paymentInfo.put("language", "ko");
        paymentInfo.put("custom_data", "json_object");
        paymentInfo.put("notice_url", "결제 결과를 받을 url");

        List<Map<String, String>> payMethods = new ArrayList<>();
        Map<String, String> cardMethod = new HashMap<>();
        cardMethod.put("pg", "INIpayTest");
        cardMethod.put("pay_method", "card");
        cardMethod.put("label", "신용/체크카드");
        payMethods.add(cardMethod);

        Map<String, String> naverPayMethod = new HashMap<>();
        naverPayMethod.put("pg", "INIpayTest");
        naverPayMethod.put("pay_method", "naverpay");
        naverPayMethod.put("label", "네이버페이");
        payMethods.add(naverPayMethod);

        // Add other payment methods

        paymentInfo.put("pay_methods", payMethods);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("payment_info", paymentInfo);
        requestBody.put("expired_at", 1699999999);

        WebClient wc = WebClient.create("https://api.iamport.co/api/supplements/v1/link/payment");

        String response = wc.post()
                .header("Authorization", "Bearer " + token)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }

    @Override
    public PaymentServiceReq requestPaymentForm(Long consultationId) {
        Consultation consultation = consultationUseCase.searchConsultationInfo(consultationId);

        return PaymentServiceReq.builder()
                .consultationId(consultation.getId())
                .consulter(consultation.getConsulter())
                .consulterEmail(consultation.getConsulterEmail())
                .consultant(consultation.getConsultant())
                .consultationType(consultation.getConsultationType())
                .cost(consultation.getCost())
                .consultationAt(consultation.getConsultationAt())
                .build();
    }

    @Override
    public void requestPayment(Long consultationId) {
        Consultation consultation = consultationUseCase.searchConsultationInfo(consultationId);

        // 임시 결제 정보를 저장
        paymentRepo.save(Payment.builder()
                .consultation(consultation)
                .paymentUid(null)
                .cost(consultation.getCost())
                .paidAt(null)
                .status(PaymentStatus.READY)
                .build());
    }

    // 결제 검증
    @Override
    public IamportResponse<com.siot.IamportRestClient.response.Payment> paymentByCallback(PaymentCallbackReq request) {
        String impKey = paymentProperty.getImpKey();
        String impSecret = paymentProperty.getImpSecret();

        IamportClient iamportClient = new IamportClient(impKey, impSecret);
        try {
            // 결제 단건 조회 (포트원)
            IamportResponse<com.siot.IamportRestClient.response.Payment> iamportResponse = iamportClient.paymentByImpUid(request.getPaymentUid());
            // 예약 내역 조회
            Payment payment = paymentRepo.findByConsultationId(request.getConsultationId())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CONSULTATION));
            // 결제 완료가 아니면
            if (!iamportResponse.getResponse().getStatus().equals("paid")) {
                // 임시 결제 삭제
                paymentRepo.delete(payment);
                throw new RuntimeException("결제 미완료");
            }

            // DB에 저장된 결제 금액
            Integer cost = payment.getCost();
            // 실 결제 금액
            int iamportCost = iamportResponse.getResponse().getAmount().intValue();

            // 결제 금액 검증
            if (iamportCost != cost) {
                // 임시 결제 삭제
                paymentRepo.delete(payment);

                // 결제금액 위변조로 의심되는 결제금액을 취소
                iamportClient.cancelPaymentByImpUid(
                        new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(iamportCost)));

                throw new RuntimeException("결제금액 위변조 의심");
            }

            // 결제 상태 변경
            payment.changePaymentBySuccess(PaymentStatus.PAID, iamportResponse.getResponse().getImpUid(), LocalDateTime.now());

            return iamportResponse;

        } catch (IamportResponseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PaymentSearchRes searchPaymentInfo(Long consultationId) {
        Payment payment = paymentRepo.findByConsultationId(consultationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CONSULTATION));

        return PaymentSearchRes.builder()
                .paymentUid(payment.getPaymentUid())
                .paymentId(payment.getId())
                .status(payment.getStatus())
                .consultationId(payment.getConsultation())
                .paidAt(payment.getPaidAt())
                .cost(payment.getCost())
                .build();
    }
}