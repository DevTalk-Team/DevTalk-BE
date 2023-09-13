package com.devtalk.payment.paymentservice.application.port.out.client.dto;

import lombok.Builder;
import lombok.Getter;

public class MemberRes<T> {
    String code;
    String message;
    T result;

    @Builder
    @Getter
    public static class MemberInfoRes{

        private String name;
        private String email;
//        private String phoneNumber;
    }
}
