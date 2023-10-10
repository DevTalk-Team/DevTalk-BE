package com.devtalk.consultation.consultationservice.consultation.application.port.out.client.dto;

import com.devtalk.consultation.consultationservice.consultation.domain.member.MemberType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MemberReq {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ConsultantReq {
        private Long consultantId;
        private String name;
        private MemberType memberType;
    }

    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ConsulterReq {
        private Long consulterId;
        private String name;
        private MemberType memberType;
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
