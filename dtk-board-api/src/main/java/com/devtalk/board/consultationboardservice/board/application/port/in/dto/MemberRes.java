package com.devtalk.board.consultationboardservice.board.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberRes {
    private String code;
    private String message;
    private MemberInfoRes result;

    @Getter
    @Builder
    public static class MemberInfoRes{
        private String name;
        private String email;
    }
}

