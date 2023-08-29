package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.application.port.in.dto.SignUpReq;

public interface SignUpUseCase {
    void signUp(SignUpReq req);
    boolean checkDuplicatedEmail(String email);
}