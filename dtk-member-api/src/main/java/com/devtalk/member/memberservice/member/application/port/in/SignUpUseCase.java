package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.application.port.in.dto.MemberReq;
import com.devtalk.member.memberservice.member.domain.member.Member;

public interface SignUpUseCase {
    Member signUp(MemberReq.SignUpReq req);
    void checkDuplicatedEmail(String email);
}
