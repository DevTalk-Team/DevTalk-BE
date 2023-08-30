package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.application.port.in.dto.SignUpReq;
import com.devtalk.member.memberservice.member.domain.member.Member;

public interface SignUpUseCase {
    Member signUp(SignUpReq req);
    boolean checkDuplicatedEmail(String email);
}
