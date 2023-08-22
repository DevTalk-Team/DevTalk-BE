package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.success.SuccessResponseNoResult;
import com.devtalk.member.memberservice.member.application.port.in.VerifyEmailUseCase;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.devtalk.member.memberservice.global.success.SuccessCode.AUTH_CODE_SUCCESS;
import static com.devtalk.member.memberservice.global.success.SuccessCode.SENDING_AUTH_CODE_SUCCESS;

@RestController
@RequiredArgsConstructor
public class VerifyEmailApiController {

    private final VerifyEmailUseCase verifyEmailUseCase;

    /* 이메일 인증 코드 보내기 */
    @GetMapping("/members/auth-code")
    public SuccessResponseNoResult sendMail(@Email @RequestParam String email) {
        verifyEmailUseCase.sendAuthCode(email);
        return new SuccessResponseNoResult(SENDING_AUTH_CODE_SUCCESS);
    }

    /* 인증 코드 확인 */
    @PostMapping("/members/auth-code")
    public SuccessResponseNoResult verifyAuthCode(@Email @RequestParam String email,
                                                  @RequestParam String authCode) {
        verifyEmailUseCase.verifyAuthCode(email, authCode);
        return new SuccessResponseNoResult(AUTH_CODE_SUCCESS);
    }
}