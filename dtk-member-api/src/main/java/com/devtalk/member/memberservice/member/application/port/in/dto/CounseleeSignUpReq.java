package com.devtalk.member.memberservice.member.application.port.in.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 사용자로부터 입력을 받는 입력 모델,
 * 유스케이스 별로 전용 입력 모델 만들기
 */
@Getter
public class CounseleeSignUpReq {

    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate birthDate;

    // 생성자 내에서 유효성 규칙 검증
    public CounseleeSignUpReq(String email, String password, String phoneNumber, String birthDate) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthDate = LocalDate.parse(birthDate, DateTimeFormatter.ISO_DATE);
    }
}
