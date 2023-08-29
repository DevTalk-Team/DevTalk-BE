package com.devtalk.payment.paymentservice.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class RefundReq {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class RefundPortOneReq{
        private String imp_uid;
        private String merchant_uid;
        private Integer amount;
        private Extra extra;

        @Builder
        @Getter
        public static class Extra{
            private String requester;
        }
    }
}
