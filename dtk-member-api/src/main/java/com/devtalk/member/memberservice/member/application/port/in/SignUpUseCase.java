package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.application.port.in.dto.SignUpReq;

public interface SignUpUseCase {
    void consulterSignUp(SignUpReq req);
    void consultantSignUp(SignUpReq req);
    boolean checkDuplicatedEmail(String email);
}
