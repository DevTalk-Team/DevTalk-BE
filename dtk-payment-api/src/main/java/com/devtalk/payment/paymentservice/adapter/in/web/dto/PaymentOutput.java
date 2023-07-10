package com.devtalk.payment.paymentservice.adapter.in.web.dto;

import com.devtalk.payment.paymentservice.domain.Payment;
import lombok.Data;

@Data
public class PaymentOutput {
    private String code;
    private String message;
    private Payment result;
}
