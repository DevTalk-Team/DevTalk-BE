package com.devtalk.member.memberservice.member.application.port.in;

public interface FindProfileUseCase {
    String findEmail(String name, String phoneNumber);
    void sendTempPassword(String name, String email);
    void changePassword(String password, String newPassword);
}
