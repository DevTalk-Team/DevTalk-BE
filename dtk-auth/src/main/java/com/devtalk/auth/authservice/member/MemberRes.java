package com.devtalk.auth.authservice.member;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class MemberRes {
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private MemberType memberType;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
}
