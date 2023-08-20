package com.devtalk.member.memberservice.member.application.port.in.dto;

import com.devtalk.member.memberservice.member.adapter.in.web.dto.SignUpInput;
import com.devtalk.member.memberservice.member.domain.member.RoleType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class SignUpReq {

    private RoleType roleType;

    private String name;
    private String email;
    private String password;
    private String checkPassword;
    private String phoneNumber;

    private List<String> field;

    public static SignUpReq toReq(SignUpInput input, RoleType roleType) {
        return SignUpReq.builder()
                .roleType(roleType)
                .email(input.getEmail())
                .password(input.getPassword())
                .checkPassword(input.getCheckPassword())
                .name(input.getName())
                .phoneNumber(input.getPhoneNumber())
                .field(input.getField())
                .build();
    }

}
