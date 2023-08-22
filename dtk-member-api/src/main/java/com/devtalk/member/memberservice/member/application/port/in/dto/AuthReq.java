package com.devtalk.member.memberservice.member.application.port.in.dto;

import com.devtalk.member.memberservice.member.adapter.in.web.dto.LogInInput;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthReq {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class LogInReq {
        private String email;
        private String password;

        public static LogInReq toReq(LogInInput input) {
            return new LogInReq(input.getEmail(), input.getPassword());
        }
    }
}
