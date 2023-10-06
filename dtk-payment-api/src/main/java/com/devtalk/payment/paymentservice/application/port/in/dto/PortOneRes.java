package com.devtalk.payment.paymentservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

public class PortOneRes {

    @Getter
    @Builder
    public static class PortOneLinkRes {
        private String shortenedUrl;
    }
}
