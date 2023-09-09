package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.application.port.in.dto.MyPageRes;

public interface MyPageUseCase {
    MyPageRes.InfoRes getMyPage(String token);
}
