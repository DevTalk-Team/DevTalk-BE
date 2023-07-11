package com.devtalk.member.memberservice.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Counselor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    //휴대폰 번호
    @Column(nullable = false, length = 10)
    private String phoneNumber;

    //이름
    @Column(nullable = false, length = 10)
    private String name;

    //소속
    @Column(nullable = false, length = 20)
    private String company;

    //상담 가능 분야

}
