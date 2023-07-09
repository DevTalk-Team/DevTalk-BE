package com.devtalk.payment.paymentservice.adapter.in.web.dto;

import lombok.Data;

@Data
class PaymentOutput {
    private String paymentId;
    private String paymentPGID;
    private String impUID;
}
