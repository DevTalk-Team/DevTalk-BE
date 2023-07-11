package com.devtalk.member.memberservice.member.adapter.in.web.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CounseleeSignUpInput {

    private String email;

    private String password;

    private String phoneNumber;

    private String birthDate;

    //관심 분야
}
