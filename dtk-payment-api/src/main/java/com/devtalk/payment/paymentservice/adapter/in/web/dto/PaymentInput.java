package com.devtalk.payment.paymentservice.adapter.in.web.dto;

import lombok.Data;

@Data
class PaymentInput {
    private String reservationId;
    private String processType;
    private Integer cost;
//    private String paymentPGID;
}
