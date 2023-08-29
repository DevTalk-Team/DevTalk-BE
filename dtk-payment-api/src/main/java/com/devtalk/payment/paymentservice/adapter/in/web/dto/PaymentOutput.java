package com.devtalk.payment.paymentservice.adapter.in.web.dto;

import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.payment.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

public class PaymentOutput {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class PaymentSearchOutput{
        private String paymentId;
        private Consultation consultationId;
        private String paymentUid;
        private Integer cost;
        private LocalDateTime paidAt;
        private PaymentStatus status;
    }
}
