package com.devtalk.payment.paymentservice.application.port.in.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailMessageReq {
    private String to;
    private String subject;
    private String message;
}
