package com.devtalk.payment.paymentservice.application;

import com.devtalk.payment.global.code.ErrorCode;
import com.devtalk.payment.global.error.exception.IncorrectException;
import com.devtalk.payment.global.error.exception.NotFoundException;
import com.devtalk.payment.paymentservice.application.port.in.PaymentUseCase;
import com.devtalk.payment.paymentservice.application.port.in.RefundUseCase;
import com.devtalk.payment.paymentservice.application.port.in.dto.PortOneRes;
import com.devtalk.payment.paymentservice.application.port.out.repository.ConsultationRepo;
import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentQueryableRepo;
import com.devtalk.payment.paymentservice.application.port.out.repository.PaymentRepo;
import com.devtalk.payment.paymentservice.application.port.out.repository.RefundRepo;
import com.devtalk.payment.paymentservice.application.validator.PaymentValidator;
import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import com.devtalk.payment.paymentservice.domain.refund.Refund;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import static com.devtalk.payment.paymentservice.application.port.in.dto.PortOneRes.*;
import static com.devtalk.payment.paymentservice.application.port.in.dto.RefundReq.*;

@Service
@RequiredArgsConstructor
@Slf4j
class RefundService implements RefundUseCase {
    private final PaymentUseCase paymentUseCase;
    private final PaymentRepo paymentRepo;
    private final PaymentQueryableRepo paymentQueryableRepo;
    private final ConsultationRepo consultationRepo;
    private final RefundRepo refundRepo;
    private final PaymentValidator paymentValidator;

    @Override
    public String getEmailHtmlConsultationRefundInfo(Consultation consultation) {
        return null;
    }

    @Override
    @Transactional
    public void cancelPayment(Long consultationId, Long userId) {
        Consultation consultation = consultationRepo.findById(consultationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CONSULTATION));
        Payment payment = paymentQueryableRepo.findByConsultationId(consultationId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CONSULTATION));

        paymentValidator.validateItIsPaid(payment);

        WebClient wc = WebClient.create("https://api.iamport.kr/payments/cancel");
        PortOneRefundRes response = wc.post()
                .header("Authorization", "Bearer " + paymentUseCase.getToken())
                .body(BodyInserters.fromValue(RefundPortOneReq.of(payment)))
                .retrieve()
                .bodyToMono(PortOneRefundRes.class)
                .block();

        payment.changePaymentByCanceled();
        consultation.changeConsultationByCanceled();
        saveRefundInfo(payment, consultation);

        if (response.getCode() != 0) {
            throw new IncorrectException(ErrorCode.INVALID_REFUND_REQUEST);
        }
    }

    @Override
    public void saveRefundInfo(Payment payment, Consultation consultation) {
        Refund refund = Refund.builder()
                .refundCost(payment.getCost())
                .consultationId(consultation)
                .paymentId(payment)
                .merchantId(payment.getMerchantId())
                .build();
        refundRepo.save(refund);
    }
}
