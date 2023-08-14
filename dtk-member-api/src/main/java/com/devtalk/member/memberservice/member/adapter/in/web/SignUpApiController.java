package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.SuccessResponseNoResult;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultantSignUpInput;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsulterSignUpInput;
import com.devtalk.member.memberservice.member.application.MailService;
import com.devtalk.member.memberservice.member.application.port.in.SignUpUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.SignUpReq;
import com.devtalk.member.memberservice.member.domain.RoleType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.devtalk.member.memberservice.global.SuccessCode.*;

@RestController
@RequiredArgsConstructor
public class SignUpApiController {

    private final SignUpUseCase signUpUseCase;
    private final MailService mailService;

    /* 이메일 중복 확인 */
    @GetMapping("/members/check-email")
    public SuccessResponseNoResult isDuplicatedEmail(@Email @RequestParam String email) {
        signUpUseCase.checkDuplicatedEmail(email);
        return new SuccessResponseNoResult(CHECK_EMAIL_DUPLICATION_SUCCESS);
    }

    /* 이메일 인증 */
    @GetMapping("/members/auth-code")
    public void sendMail() {
        mailService.sendMail("pkl4693@naver.com");
    }

    /* 멘티 회원가입 */
    @PostMapping("/consulter")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponseNoResult consulterSignUp(@Valid @RequestBody ConsulterSignUpInput input) {
        SignUpReq req = SignUpReq.builder()
                .roleType(RoleType.CONSULTER)
                .email(input.getEmail())
                .password(input.getPassword())
                .checkPassword(input.getCheckPassword())
                .name(input.getName())
                .phoneNumber(input.getPhoneNumber())
                .birthDate(LocalDate.parse(input.getBirthDate(), DateTimeFormatter.ISO_DATE))
                .field(input.getField())
                .build();
        signUpUseCase.consulterSignUp(req);
        return new SuccessResponseNoResult(CONSULTER_SIGNUP_SUCCESS);
    }

    /* 전문가 회원가입 */
    @PostMapping("/consultant")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponseNoResult consultantSignUp(@Valid @RequestBody ConsultantSignUpInput input) {
        SignUpReq req = SignUpReq.builder()
                .roleType(RoleType.CONSULTANT)
                .email(input.getEmail())
                .password(input.getPassword())
                .checkPassword(input.getCheckPassword())
                .name(input.getName())
                .phoneNumber(input.getPhoneNumber())
                .company(input.getCompany())
                .field(input.getField())
                .build();
        signUpUseCase.consultantSignUp(req);
        return new SuccessResponseNoResult(CONSULTANT_SIGNUP_SUCCESS);
    }

}
