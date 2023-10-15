package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.application.port.out.dto.MyPageRes;

public interface MyPageUseCase {
    void checkPassword(String email, String password, String checkPassword);
    MyPageRes.InfoRes getMyPage(String email);
    MyPageRes.InfoRes updateMyPage(String email);
}
