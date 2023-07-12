package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.application.port.in.dto.CounseleeSignUpReq;
import com.devtalk.member.memberservice.member.domain.Counselee;

public interface SignUpUseCase {

    //멘티 회원가입
    //return CounseleeSignUpRes
    void signUp(CounseleeSignUpReq req);
}
