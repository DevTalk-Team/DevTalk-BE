package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.application.port.in.dto.AuthReq;
import com.devtalk.member.memberservice.member.application.port.out.dto.AuthRes;

public interface AuthUseCase {
    AuthRes.LogInRes login(AuthReq.LogInReq req);
    void logout(String token);
//    String reissueAccessToken(String refreshToken);
}
