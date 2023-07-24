package com.devtalk.payment.paymentservice.adapter.in.web.dto;

import lombok.*;

public class PaymentInput {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class PaymentRequestInput{
        private Long consultationId;
        private String consultationType;
        private Integer cost;
    //    private String paymentPGID;
    }

}
