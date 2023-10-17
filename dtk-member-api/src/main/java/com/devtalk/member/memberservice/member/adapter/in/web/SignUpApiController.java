package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.error.ErrorResponse;
import com.devtalk.member.memberservice.global.success.SuccessResponseNoResult;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.MemberInput;
import com.devtalk.member.memberservice.member.adapter.out.producer.KafkaProducer;
import com.devtalk.member.memberservice.member.application.port.in.ConsultantInfoUseCase;
import com.devtalk.member.memberservice.member.application.port.in.SignUpUseCase;
import com.devtalk.member.memberservice.member.application.port.in.VerifyEmailUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.MemberReq;
import com.devtalk.member.memberservice.member.domain.member.Member;
import com.devtalk.member.memberservice.member.domain.member.MemberType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.devtalk.member.memberservice.global.success.SuccessCode.*;

@Tag(name = "회원가입", description = "이메일 중복 확인, 인증 코드, 회원가입")
@RestController
@RequestMapping("/member/signup")
@RequiredArgsConstructor
public class SignUpApiController {
    private final SignUpUseCase signUpUseCase;
    private final ConsultantInfoUseCase consultantInfoUseCase;
    private final VerifyEmailUseCase verifyEmailUseCase;
    private final KafkaProducer kafkaProducer;
    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }


    /* 이메일 인증 코드 보내기 */
    @Operation(summary = "회원가입 - 이메일 인증 코드 요청", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseNoResult.class)))
    })
    @GetMapping("/auth-code")
    public SuccessResponseNoResult sendAuthCode(@Email @RequestParam String email) {
        verifyEmailUseCase.sendAuthCode(email);
        return new SuccessResponseNoResult(SENDING_AUTH_CODE_SUCCESS);
    }

    /* 인증 코드 확인 */
    @Operation(summary = "회원가입 - 이메일 인증 코드 확인", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseNoResult.class))),
            @ApiResponse(description = "인증 코드 불일치", responseCode = "409", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/auth-code")
    public ResponseEntity<?> verifyAuthCode(@Email @RequestParam String email,
                                            @RequestParam String authCode) {
        verifyEmailUseCase.verifyAuthCode(email, authCode);
        return SuccessResponseNoResult.toResponseEntity(AUTH_CODE_SUCCESS);
    }

    /* 멘티 회원가입 */
    @Operation(summary = "멘티 회원가입", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseNoResult.class)))
    })
    @PostMapping("/consulter")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> consulterSignUp(@Valid @RequestBody MemberInput.SignUpInput input) {
        MemberReq.SignUpReq req = input.toReq(MemberType.CONSULTER);
        Member member = signUpUseCase.signUp(req);
        kafkaProducer.sendMember(member);
        return SuccessResponseNoResult.toResponseEntity(CONSULTER_SIGNUP_SUCCESS);
    }
    /* 전문가 회원가입 */
    @Operation(summary = "전문가 회원가입", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseNoResult.class)))
    })
    @PostMapping("/consultant")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> consultantSignUp(@Valid @RequestBody MemberInput.SignUpInput input) {
        MemberReq.SignUpReq req = input.toReq(MemberType.CONSULTANT);
        Member member = signUpUseCase.signUp(req);
        kafkaProducer.sendMember(member);
        return SuccessResponseNoResult.toResponseEntity(CONSULTANT_SIGNUP_SUCCESS);
    }


    /* 이메일 중복 확인 */
    @Operation(summary = "회원가입 - 이메일 중복 확인", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseNoResult.class))),
            @ApiResponse(description = "가입 이메일 중복", responseCode = "409", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/check-email")
    public ResponseEntity<?> isDuplicatedEmail(@Email @RequestParam String email) {
        signUpUseCase.checkDuplicatedEmail(email);
        return SuccessResponseNoResult.toResponseEntity(CHECK_EMAIL_DUPLICATION_SUCCESS);
    }
}
