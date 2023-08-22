package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.success.SuccessResponse;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.LogInInput;
import com.devtalk.member.memberservice.member.application.port.in.AuthUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.AuthReq.LogInReq;
import com.devtalk.member.memberservice.member.application.port.in.dto.AuthRes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.devtalk.member.memberservice.global.success.SuccessCode.LOGIN_SUCCESS;
import static com.devtalk.member.memberservice.member.application.port.in.dto.AuthReq.LogInReq.toReq;

@RestController
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthUseCase authUseCase;

    @PostMapping("/login")
    public SuccessResponse<AuthRes.LogInRes> login(@Valid @RequestBody LogInInput input) {
        LogInReq req = toReq(input);
        AuthRes.LogInRes res = authUseCase.login(req);
        return new SuccessResponse<>(LOGIN_SUCCESS, res);
    }


}
