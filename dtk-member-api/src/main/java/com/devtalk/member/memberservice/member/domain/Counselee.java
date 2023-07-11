package com.devtalk.member.memberservice.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Counselee {

    private Long id;
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDate birthDate;
    //관심 분야

    public Counselee(String email, String password, String phoneNumber, LocalDate birthDate) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }
}
