package com.devtalk.payment.paymentservice.application.port.in.dto;

import com.devtalk.payment.paymentservice.domain.Payment;
import lombok.Data;

@Data
public class PaymentRes {
    private String code;
    private String message;
    private Payment result;
}
