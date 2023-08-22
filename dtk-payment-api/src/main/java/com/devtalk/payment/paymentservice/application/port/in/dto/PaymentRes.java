package com.devtalk.payment.paymentservice.application.port.in.dto;

import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.payment.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

public class PaymentRes {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Builder
    public static class PaymentSearchRes{
        private Long paymentId;
        private Consultation consultationId;
        private String paymentUid;
        private Integer cost;
        private LocalDateTime paidAt;
        private PaymentStatus status;
    }

    @AllArgsConstructor
    @Getter
    public static class PaymentServiceRes {
        private Long paymentId;
        private String paymentUid;
    }
}
