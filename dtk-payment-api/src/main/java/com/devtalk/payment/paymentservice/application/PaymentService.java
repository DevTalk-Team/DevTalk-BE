package com.devtalk.payment.paymentservice.application;

import com.devtalk.payment.global.code.ErrorCode;
import com.devtalk.payment.global.error.exception.NotFoundException;
import com.devtalk.payment.paymentservice.adapter.in.web.dto.ConsultationInput;
import com.devtalk.payment.paymentservice.adapter.out.producer.KafkaProducer;
import com.devtalk.payment.paymentservice.application.port.in.ConsultationUseCase;
import com.devtalk.payment.paymentservice.application.port.in.EmailUseCase;
import com.devtalk.payment.paymentservice.application.port.in.PaymentUseCase;
import com.devtalk.payment.paymentservice.application.port.out.repository.ConsultationRepo;
import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentRepo;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.consultation.ProcessStatus;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import com.devtalk.payment.paymentservice.domain.payment.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.*;

import static com.devtalk.payment.paymentservice.application.port.in.dto.PaymentReq.*;
import static com.devtalk.payment.paymentservice.application.port.in.dto.PaymentRes.*;
import static com.devtalk.payment.paymentservice.application.port.in.dto.PortOneReq.*;
import static com.devtalk.payment.paymentservice.application.port.in.dto.PortOneRes.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PaymentService implements PaymentUseCase {
    private final ConsultationRepo consultationRepo;
    private final PaymentRepo paymentRepo;
    private final ConsultationUseCase consultationUseCase;
    private final EmailUseCase emailUseCase;
    private final KafkaProducer kafkaProducer;

    @Value("${portone.imp_key}") private String impKey;
    @Value("${portone.imp_secret}") private String impSecret;
    @Value("${portone.imp_uid}") private String impUid;
    @Value("${portone.webhook_url}") private String webhookUrl;

    // 결제 토큰 생성 (포트원 API사용을 위해 필요함)
    @Override
    public String getToken() {
        Map<String, Object> params = new HashMap<>();
        params.put("imp_key", impKey);
        params.put("imp_secret", impSecret);

        WebClient wc = WebClient.create("https://api.iamport.kr/users/getToken");
        PortOneTokenRes response = wc.post()
                .bodyValue(params)
                .retrieve()
                .bodyToMono(PortOneTokenRes.class)
                .block();

        return response.getResponse().getAccessToken();
    }

    @Override
    @Transactional
    public String getPaymentLink(Long consultationId, Long userId) {
        Consultation consultation = consultationUseCase.searchConsultationInfo(consultationId);
        WebClient wc = WebClient.create("https://api.iamport.co/api/supplements/v1/link/payment");

        PortOneLinkRes response = wc.post()
                .header("Authorization", "Bearer " + getToken())
                .bodyValue(toPortOneStringReq(consultation))
                .retrieve()
                .bodyToMono(PortOneLinkRes.class)
                .block();

        return response.getShortenedUrl();
    }

    @Override
    public void updatePaymentStatus(WebhookReq webhookReq) {
        String status = webhookReq.getStatus();

        if (status.equals("paid")) {
            Payment payment = paymentRepo.findByMerchantId(webhookReq.getMerchant_uid())
                    .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CONSULTATION));
            Consultation consultation = payment.getConsultation();
            payment.changePaymentBySuccess(webhookReq.getImp_uid());
            consultation.changeConsultationByWaitingConsultation();
            kafkaProducer.sendPaymentStatus("payment-status", payment);
            emailUseCase.sendPaymentSuccessEmail(consultation);
        }
    }

    @Override
    public PaymentSearchRes searchPaymentInfo(Long consultationId) {
        Payment payment = paymentRepo.findByConsultationId(consultationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CONSULTATION));

        return PaymentSearchRes.of(payment);
    }

    // 테스트용
    @Override
    public void createPaymentInfo(ConsultationInput consultationInput) {
        Consultation consultation = Consultation.builder()
                .cost(consultationInput.getCost())
                .consultantName(consultationInput.getConsultant())
                .consulterName(consultationInput.getConsultant())
                .consultationType(consultationInput.getConsultationType())
                .consulterEmail(consultationInput.getConsulterEmail())
                .merchantId(UUID.randomUUID().toString())
                .consultationAt(LocalDateTime.now())
                .processStatus(ProcessStatus.APPROVED)
                .build();

        consultationRepo.save(consultation);
        paymentRepo.save(Payment.builder()
                .consultation(consultation)
                .paymentUid(null)
                .merchantId(consultation.getMerchantId())
                .cost(consultation.getCost())
                .paidAt(null)
                .status(PaymentStatus.READY)
                .build());
    }

    private PortOneLinkReq toPortOneReq(Consultation consultation) {
        List<PayMethod> payMethods = Arrays.asList(
                new PayMethod("html5_inicis.INIpayTest", "card", "신용/체크카드"),
                new PayMethod("html5_inicis.INIpayTest", "naverpay", "네이버페이"),
                new PayMethod("html5_inicis.INIpayTest", "kakaopay", "카카오페이"),
                new PayMethod("tosspayments.iamporttest_3", "tosspay.tosstest", "토스페이"));

        PaymentInfo paymentInfo = PaymentInfo.builder()
                .consultationType(consultation.getConsultationType())
                .impUid(impUid)
                .amount(consultation.getCost())
                .merchantId(consultation.getMerchantId())
                .consultantName("전문가 이름")
                .currency("KRW")
                .consulterName("상담자 이름")
                .consulterTel("상담자 전화번호")
                .consulterEmail("상담자 이메일")
                .customData("json_object")
                .webhookUrl(webhookUrl)
                .payMethods(payMethods)
                .build();

        return PortOneLinkReq.builder()
                .payment_info(paymentInfo)
                .expired_at(System.currentTimeMillis() / 1000 + 1800)
                .build();
    }

    private PortOneStringReq toPortOneStringReq(Consultation consultation){
        return PortOneStringReq.builder()
                .paymentInfo(String.format(
                "{" +
                        "\"title\":\"데브톡 - %s\"," +
                        "\"user_code\":\"%s\"," +
                        "\"amount\":%d," +
                        "\"merchant_uid\":\"%s\"," +
                        "\"name\":\"%s\"," +
                        "\"currency\":\"KRW\"," +
                        "\"buyer_name\":\"%s\"," +
                        "\"buyer_tel\":\"-\"," +
                        "\"buyer_email\":\"%s\"," +
                        "\"custom_data\":\"json_object\"," +
                        "\"notice_url\":\"%s\"," +
                        "\"pay_methods\":[" +
                            "{\"pg\":\"html5_inicis.INIpayTest\",\"pay_method\":\"card\",\"label\":\"신용/체크카드\"}," +
                            "{\"pg\":\"html5_inicis.INIpayTest\",\"pay_method\":\"naverpay\",\"label\":\"네이버페이\"}," +
                            "{\"pg\":\"html5_inicis.INIpayTest\",\"pay_method\":\"kakaopay\",\"label\":\"카카오페이\"}," +
                            "{\"pg\":\"tosspayments.iamporttest_3\",\"pay_method\":\"tosspay.tosstest\",\"label\":\"토스페이\"}" +
                            "]" +
                        "}",
                            consultation.getConsultationType(),
                            impUid,
                            consultation.getCost(),
                            consultation.getMerchantId(),
                            consultation.getConsultationType(),
                            consultation.getConsulterName(),
                            consultation.getConsulterEmail(),
                            webhookUrl))
                .expiredAt(System.currentTimeMillis() / 1000 + 1800)
                .build();
    }
}