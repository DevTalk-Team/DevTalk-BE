package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.adapter.in.web.dto.FindProfileOutput;

public interface FindProfileUseCase {
    String findEmail(String name, String phoneNumber);
    void sendTempPassword(String name, String email);
    void changePassword(String password, String newPassword);
    FindProfileOutput.MemberOutput findMember(Long memberId);
}
