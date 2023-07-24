package com.devtalk.payment.paymentservice.application.port.in.dto;

import com.devtalk.payment.paymentservice.domain.consultation.Consultation;
import com.devtalk.payment.paymentservice.domain.payment.Payment;
import com.devtalk.payment.paymentservice.domain.payment.PaymentStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

public class PaymentRes {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class PaymentSearchRes{
        private Long paymentId;
        private Consultation consultationId;
        private String impUid;
        private String paymentPgId;
        private Integer cost;
        private Date paidAt;
        private PaymentStatus status;
    }
}
