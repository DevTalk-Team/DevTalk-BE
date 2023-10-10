package com.devtalk.product.productservice.product.application.port.in.dto;

import lombok.*;

import java.time.LocalDateTime;

public class MemberReq {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ConsultantReq {
        private Long consultantId;
        private String name;
        private MemberType memberType;
        //가격은 상품에서도 쓰기 위함
        private Integer F2F_Cost;
        private Integer NF2F_Cost;

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