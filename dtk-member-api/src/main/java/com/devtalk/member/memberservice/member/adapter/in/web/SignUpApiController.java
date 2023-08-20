package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.SuccessResponseNoResult;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.SignUpInput;
import com.devtalk.member.memberservice.member.application.port.in.SignUpUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.SignUpReq;
import com.devtalk.member.memberservice.member.domain.member.RoleType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.devtalk.member.memberservice.global.SuccessCode.*;
import static com.devtalk.member.memberservice.member.application.port.in.dto.SignUpReq.toReq;

@RestController
@RequiredArgsConstructor
public class SignUpApiController {

    private final SignUpUseCase signUpUseCase;

    /* 멘티 회원가입 */
    @PostMapping("/consulter")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponseNoResult consulterSignUp(@Valid @RequestBody SignUpInput input) {
        SignUpReq req = toReq(input, RoleType.CONSULTER);
        signUpUseCase.signUp(req);
        return new SuccessResponseNoResult(CONSULTER_SIGNUP_SUCCESS);
    }
    /* 전문가 회원가입 */
    @PostMapping("/consultant")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponseNoResult consultantSignUp(@Valid @RequestBody SignUpInput input) {
        SignUpReq req = toReq(input, RoleType.CONSULTANT);
        signUpUseCase.signUp(req);
        return new SuccessResponseNoResult(CONSULTANT_SIGNUP_SUCCESS);
    }

    /* 이메일 중복 확인 */
    @GetMapping("/members/check-email")
    public SuccessResponseNoResult isDuplicatedEmail(@Email @RequestParam String email) {
        signUpUseCase.checkDuplicatedEmail(email);
        return new SuccessResponseNoResult(CHECK_EMAIL_DUPLICATION_SUCCESS);
    }
}
