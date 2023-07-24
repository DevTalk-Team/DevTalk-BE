package com.devtalk.member.memberservice.member.application.port.in.dto;

import com.devtalk.member.memberservice.member.domain.RoleType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class SignUpReq {

    private RoleType roleType;

    private String email;
    private String password;
    private String checkPassword;
    private String name;
    private String phoneNumber;

    private List<String> field;

    private LocalDate birthDate;
    private String company;

}
