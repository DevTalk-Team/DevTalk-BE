package com.devtalk.payment.paymentservice.adapter.in.web.dto;

import lombok.*;

import java.time.LocalDateTime;

public class PaymentInput {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Builder
    public static class PaymentRequestInput{
        private Long consultationId;
        private String consulter;
        private String consulterEmail;
        private String consultant;
        private String consultationType;
        private Integer cost;
        private LocalDateTime consultationAt;
    }

}
