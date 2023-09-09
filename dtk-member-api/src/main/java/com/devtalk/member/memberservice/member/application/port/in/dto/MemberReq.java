package com.devtalk.member.memberservice.member.application.port.in.dto;

import com.devtalk.member.memberservice.member.domain.member.MemberType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class MemberReq {

    @Builder
    @Getter
    public static class SignUpReq {
        private MemberType memberType;
        private String name;
        private String email;
        private String password;
        private String checkPassword;
        private String phoneNumber;
        private List<String> category;
    }
}
