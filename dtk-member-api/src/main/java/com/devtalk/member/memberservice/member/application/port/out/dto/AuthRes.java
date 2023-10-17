package com.devtalk.member.memberservice.member.application.port.out.dto;

import lombok.Builder;
import lombok.Getter;

public class AuthRes {

    @Getter
    @Builder
    public static class LogInRes {
        private String accessToken;
        private String tokenType;
        private String email; // refreshToken 키 값 (redis)
        private String refreshToken;
    }

    @Getter
    @Builder
    public static class TokenRes {
        private String accessToken;
        private String tokenType;
    }
}
