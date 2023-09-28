package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.application.port.out.dto.MyPageRes;

public interface MyPageUseCase {
    MyPageRes.InfoRes getMyPage(String email);
    void checkPassword(String email, String password, String checkPassword);
}
