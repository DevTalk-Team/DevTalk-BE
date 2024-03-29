package com.devtalk.payment.paymentservice.application.port.in.dto;

import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import com.devtalk.payment.paymentservice.domain.payment.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

public class PaymentRes {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Builder
    public static class PaymentSearchRes{
        private String paymentId;
        private Consultation consultationId;
        private String paymentUid;
        private Integer cost;
        private LocalDateTime paidAt;
        private PaymentStatus status;

        public static PaymentSearchRes of(Payment payment) {
            return PaymentSearchRes.builder()
                    .paymentUid(payment.getPaymentUid())
                    .status(payment.getStatus())
                    .consultationId(payment.getConsultation())
                    .paidAt(payment.getPaidAt())
                    .cost(payment.getCost())
                    .build();
        }
    }
}
