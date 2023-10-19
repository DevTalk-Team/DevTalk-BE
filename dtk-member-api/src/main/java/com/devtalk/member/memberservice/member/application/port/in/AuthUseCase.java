package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.application.port.in.dto.AuthReq;
import com.devtalk.member.memberservice.member.application.port.out.dto.AuthRes;

public interface AuthUseCase {
    AuthRes.LoginRes login(AuthReq.LogInReq req);
    AuthRes.LogoutRes logout(String token, String userEmail);
    AuthRes.TokenRes reissueAccessToken(String refreshToken, String email);
}
