package com.devtalk.payment.paymentservice.application;

import com.devtalk.payment.global.code.ErrorCode;
import com.devtalk.payment.global.config.property.PaymentProperty;
import com.devtalk.payment.global.error.exception.NotFoundException;
import com.devtalk.payment.paymentservice.adapter.out.producer.KafkaProducer;
import com.devtalk.payment.paymentservice.application.port.in.ConsultationUseCase;
import com.devtalk.payment.paymentservice.application.port.in.EmailUseCase;
import com.devtalk.payment.paymentservice.application.port.in.PaymentUseCase;
import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentRepo;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
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
    private final KafkaProducer kafkaProducer;
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
    @Transactional
    public String getPaymentLink(Long consultationId) {
        String impUid = paymentProperty.getImpUid();
        String webhookUrl = "https://a04d-211-213-255-27.ngrok-free.app/payment/webhook";
        Consultation consultation = consultationUseCase.searchConsultationInfo(consultationId);
        String paymentInfo = String.format(
                "{" +
                        "\"title\":\"데브톡 - %s\"," +
                        "\"user_code\":\"%s\"," +
                        "\"amount\":%d," +
                        "\"merchant_uid\":\"%s\"," +
                        "\"name\":\"%s\"," +
                        "\"currency\":\"KRW\"," +
                        "\"buyer_name\":\"%s\"," +
                        "\"buyer_tel\":\"010-1234-1234\"," +
                        "\"buyer_email\":\"%s\"," +
                        "\"custom_data\":\"json_object\"," +
                        "\"notice_url\":\"%s\"," +
                        "\"pay_methods\":[" +
                        "{\"pg\":\"html5_inicis.INIpayTest\",\"pay_method\":\"card\",\"label\":\"신용/체크카드\"}," +
                        "{\"pg\":\"html5_inicis.INIpayTest\",\"pay_method\":\"naverpay\",\"label\":\"네이버페이\"}," +
                        "{\"pg\":\"html5_inicis.INIpayTest\",\"pay_method\":\"kakaopay\",\"label\":\"카카오페이\"}," +
                        "{\"pg\":\"iamporttest_3\",\"pay_method\":\"tosspay.tosstest\",\"label\":\"토스페이\"}" +
                        "]" +
                        "}",
                            consultation.getConsultationType(),
                            impUid,
                            consultation.getCost(),
                            consultation.getMerchantId(),
                            consultation.getConsultationType(),
                            consultation.getConsulter(),
                            consultation.getConsulterEmail(),
                            webhookUrl);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("payment_info", paymentInfo);
        requestBody.put("expired_at", (System.currentTimeMillis()/1000 + 1800));

        WebClient wc = WebClient.create("https://api.iamport.co/api/supplements/v1/link/payment");

        String response = wc.post()
                .header("Authorization", "Bearer " + getToken())
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            // ObjectMapper를 사용하여 JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode node = objectMapper.readTree(response);
            String paymentLink = node.get("shortenedUrl").asText();
            return paymentLink;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updatePaymentStatus(WebhookReq webhookReq) {
        String paymentUid = webhookReq.getImp_uid();
        String merchantId = webhookReq.getMerchant_uid();
        String status = webhookReq.getStatus();

        if (status.equals("paid")) {
            Payment payment = paymentRepo.findByMerchantId(merchantId)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CONSULTATION));
            Consultation consultation = payment.getConsultation();
            payment.changePaymentBySuccess(paymentUid);
            consultation.changeConsultationByWaitingConsultation();
            kafkaProducer.sendPaymentStatus("payment-status", payment);
            emailUseCase.sendPaymentSuccessEmail(consultation);
        }
    }

    @Override
    public PaymentSearchRes searchPaymentInfo(Long consultationId) {
        Payment payment = paymentRepo.findByConsultationId(consultationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CONSULTATION));

        return PaymentSearchRes.builder()
                .paymentUid(payment.getPaymentUid())
                .status(payment.getStatus())
                .consultationId(payment.getConsultation())
                .paidAt(payment.getPaidAt())
                .cost(payment.getCost())
                .build();
    }
}