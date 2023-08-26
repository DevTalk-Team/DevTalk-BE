package com.devtalk.member.memberservice.member.application.port.in.dto;

import com.devtalk.member.memberservice.member.adapter.in.web.dto.LogInInput;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LogInReq {
    private String email;
    private String password;

    public static LogInReq toReq(LogInInput input) {
        return LogInReq.builder()
                .email(input.getEmail())
                .password(input.getPassword())
                .build();
    }
}
