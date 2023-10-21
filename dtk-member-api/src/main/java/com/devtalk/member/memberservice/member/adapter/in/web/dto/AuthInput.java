package com.devtalk.member.memberservice.member.adapter.in.web.dto;

import com.devtalk.member.memberservice.member.application.port.in.dto.AuthReq;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class AuthInput {
    @Data
    public static class LoginInput {
        @NotBlank(message = "아이디는 필수입니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        private final String email;
        @NotBlank(message = "비밀번호는 필수입니다.")
        private final String password;

        public AuthReq.LoginReq toReq() {
            return AuthReq.LoginReq.builder()
                    .email(email)
                    .password(password)
                    .build();
        }
    }

    @Data
    public static class ReissueInput {
        private String refreshToken;
    }
}
