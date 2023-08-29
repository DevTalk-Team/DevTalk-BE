package com.devtalk.member.memberservice.member.application.port.in;

public interface VerifyEmailUseCase {
    void sendAuthCode(String email);
    void verifyAuthCode(String email, String authCode);
}
