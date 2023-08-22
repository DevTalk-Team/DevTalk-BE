package com.devtalk.member.memberservice.member.application.port.in.dto;

import com.devtalk.member.memberservice.member.adapter.in.web.dto.LogInInput;
import lombok.*;

public class AuthRes {

    @Getter
    @Builder
    public static class LogInRes {
        private TokenDto tokenDto;

    }

}
