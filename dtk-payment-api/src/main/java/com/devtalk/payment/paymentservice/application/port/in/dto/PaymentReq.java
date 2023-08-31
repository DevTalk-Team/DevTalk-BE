package com.devtalk.payment.paymentservice.application.port.in.dto;

import lombok.*;

import java.time.LocalDateTime;

public class PaymentReq {
    @NoArgsConstructor
    @Data
    public static class WebhookReq{
        private String imp_uid;
        private String merchant_uid;
        private String status;
    }

}
