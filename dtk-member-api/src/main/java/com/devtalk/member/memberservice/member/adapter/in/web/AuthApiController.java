package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.SuccessResponse;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.LogInInput;
import com.devtalk.member.memberservice.member.application.port.in.AuthUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.LogInReq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.devtalk.member.memberservice.global.SuccessCode.LOGIN_SUCCESS;
import static com.devtalk.member.memberservice.member.application.port.in.dto.LogInReq.toReq;

@RestController
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthUseCase authUseCase;

    @GetMapping("/login")
    public SuccessResponse<String> login(@Valid @RequestBody LogInInput input) {
        LogInReq req = toReq(input);
        String token = authUseCase.login(req);
        return new SuccessResponse(LOGIN_SUCCESS, token);
    }

}
