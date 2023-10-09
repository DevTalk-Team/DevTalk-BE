package com.devtalk.payment.paymentservice.application.port.in.dto;

import com.devtalk.payment.paymentservice.domain.payment.Payment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class RefundReq {
    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class RefundPortOneReq{
        private String imp_uid;
        private String merchant_uid;
        private Integer amount;
        private Extra extra;

        @Builder
        @Getter
        static class Extra{
            private String requester;
        }
        public static RefundPortOneReq of(Payment payment) {
            return RefundPortOneReq.builder()
                    .imp_uid(payment.getPaymentUid())
                    .merchant_uid(payment.getMerchantId())
                    .amount(payment.getCost())
                    .extra(RefundPortOneReq.Extra.builder().requester("customer").build())
                    .build();
        }
    }
}
