package com.devtalk.board.consultationboardservice.board.application.port.in.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class MemberRes {
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class ProfileRes {
        private Long id;
        private MemberType memberType;
        private String name;
        private String email;
//        private String password;
        private String phoneNumber;

        private enum MemberType {
            CONSULTER, CONSULTANT
        }
    }
}

