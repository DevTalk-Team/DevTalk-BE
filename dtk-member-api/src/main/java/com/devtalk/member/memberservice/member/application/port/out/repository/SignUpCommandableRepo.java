package com.devtalk.member.memberservice.member.application.port.out.repository;

import com.devtalk.member.memberservice.member.domain.Member;

public interface SignUpCommandableRepo {
    void save(Member member);
    boolean existsByEmail(String email);
}