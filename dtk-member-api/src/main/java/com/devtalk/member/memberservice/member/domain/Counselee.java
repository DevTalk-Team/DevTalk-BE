package com.devtalk.member.memberservice.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Counselee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    //휴대폰 번호
    @Column(nullable = false, length = 20)
    private String phoneNumber;

    //생년월일
    @Column(nullable = false)
    private LocalDate birthDate;

    //관심 분야


}
