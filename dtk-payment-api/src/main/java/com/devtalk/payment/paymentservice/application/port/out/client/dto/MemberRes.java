package com.devtalk.payment.paymentservice.application.port.out.client.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class MemberRes {
    private String name;
    private String email;
    private String phoneNumber;

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ProfileRes {
        private Long id;
        private MemberType memberType;
        private String name;
        private String email;
        private String password;
        private String phoneNumber;

        private enum MemberType {
            CONSULTER, CONSULTANT
        }
    }
}
