package com.devtalk.payment.paymentservice.application.port.in.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PaymentReq {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class PaymentServiceReq {
        private String consultationId;
        private String consultationType;
        private Integer cost;
        private String paymentPgId;
    }
}
