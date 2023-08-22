package com.devtalk.payment.paymentservice.application.port.in.dto;

import lombok.Data;

@Data
public class EmailMessageRes {
    private String to;
    private String subject;
    private String message;
}
