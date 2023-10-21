package com.devtalk.member.memberservice.member.application.port.in.dto;

import com.devtalk.member.memberservice.member.adapter.in.web.dto.AuthInput;
import lombok.*;

public class AuthReq {
    @Builder
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class LoginReq {
        private String email;
        private String password;
    }
}
