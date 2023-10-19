package com.devtalk.product.productservice.product.application.port.in.dto;

import lombok.*;

import java.time.LocalDateTime;

public class MemberReq {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ConsultantReq {
        private Long consultantId;
        private Integer f2fCost;
        private Integer nf2fCost;
        private String name;
        private MemberType memberType;

        private enum MemberType {
            CONSULTER, CONSULTANT
        }
    }
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ProfileReq {
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