package com.devtalk.payment.paymentservice.application.port.in.dto;

import lombok.*;

import java.time.LocalDateTime;

public class PaymentReq {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @Builder
    // view로 전달할 결제 관련 데이터
    public static class PaymentServiceReq {
        private Long consultationId;
        private String consulter;
        private String consulterEmail;
        private String consultant;
        private String consultationType;
        private Integer cost;
        private LocalDateTime consultationAt;
    }


    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    // 결제가 이루어진 후 서버가 전달받는 데이터
    public static class PaymentCallbackReq {
        private String paymentUid; // 결제 고유 번호
        private Long consultationId; // 매칭 고유 번호
    }

}
