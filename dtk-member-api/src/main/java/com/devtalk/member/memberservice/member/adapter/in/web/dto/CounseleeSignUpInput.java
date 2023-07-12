package com.devtalk.member.memberservice.member.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CounseleeSignUpInput {

    @NotBlank(message = "아이디는 필수입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
    @NotBlank(message = "휴대폰 번호는 필수입니다.")
    private String phoneNumber;
    @NotBlank(message = "생년월일은 필수입니다.")
    private String birthDate;

    //관심 분야
}
