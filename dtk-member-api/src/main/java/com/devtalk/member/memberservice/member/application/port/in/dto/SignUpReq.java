package com.devtalk.member.memberservice.member.application.port.in.dto;

import com.devtalk.member.memberservice.member.adapter.in.web.dto.SignUpInput;
import com.devtalk.member.memberservice.member.domain.member.MemberType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SignUpReq {

    private MemberType memberType;

    private String name;
    private String email;
    private String password;
    private String checkPassword;
    private String phoneNumber;

    private List<String> category;
    
    public static SignUpReq toReq(SignUpInput input, MemberType memberType) {
        return SignUpReq.builder()
                .memberType(memberType)
                .email(input.getEmail())
                .password(input.getPassword())
                .checkPassword(input.getCheckPassword())
                .name(input.getName())
                .phoneNumber(input.getPhoneNumber())
                .category(input.getCategory())
                .build();
    }
}
