package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.security.JwtTokenProvider;
import com.devtalk.member.memberservice.global.security.MemberDetails;
import com.devtalk.member.memberservice.global.success.SuccessCode;
import com.devtalk.member.memberservice.global.success.SuccessResponse;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.LogInInput;
import com.devtalk.member.memberservice.member.application.port.in.AuthUseCase;
import com.devtalk.member.memberservice.member.application.port.out.dto.AuthRes;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.devtalk.member.memberservice.member.application.port.in.dto.AuthReq.LogInReq.toReq;

@Tag(name = "인증", description = "로그인, 로그아웃")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class AuthApiController {
    private final AuthUseCase authUseCase;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LogInInput input) {
        AuthRes.LoginRes res = authUseCase.login(toReq(input));
        return SuccessResponse.toResponseEntity(SuccessCode.LOGIN_SUCCESS, res);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, @AuthenticationPrincipal MemberDetails memberDetails) {
        String accessToken = jwtTokenProvider.resolveToken(request);
        AuthRes.LogoutRes res = authUseCase.logout(accessToken, memberDetails.getUsername());
        return SuccessResponse.toResponseEntity(SuccessCode.LOGOUT_SUCCESS, res);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request,
                                     @AuthenticationPrincipal MemberDetails memberDetails) {
        String refreshToken = jwtTokenProvider.resolveToken(request);
        AuthRes.TokenRes res = authUseCase.reissueAccessToken(refreshToken, memberDetails.getUsername());
        return SuccessResponse.toResponseEntity(SuccessCode.REISSUE_SUCCESS, res);
    }
}
