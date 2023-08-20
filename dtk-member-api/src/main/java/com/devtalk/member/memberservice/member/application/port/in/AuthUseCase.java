package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.application.port.in.dto.LogInReq;

public interface AuthUseCase {
    String login(LogInReq req);
}
