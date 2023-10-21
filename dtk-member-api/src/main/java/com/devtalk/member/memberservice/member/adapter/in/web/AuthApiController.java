package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.security.MemberDetails;
import com.devtalk.member.memberservice.global.security.TokenProvider;
import com.devtalk.member.memberservice.global.success.SuccessCode;
import com.devtalk.member.memberservice.global.success.SuccessResponse;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.AuthInput;
import com.devtalk.member.memberservice.member.application.port.in.AuthUseCase;
import com.devtalk.member.memberservice.member.application.port.out.dto.AuthRes;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증", description = "로그인, 로그아웃, AT 재발급")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class AuthApiController {
    private final AuthUseCase authUseCase;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthInput.LoginInput input) {
        AuthRes.LoginRes res = authUseCase.login(input.toReq());
        return SuccessResponse.toResponseEntity(SuccessCode.LOGIN_SUCCESS, res);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request,
                                    @AuthenticationPrincipal MemberDetails memberDetails) {
        AuthRes.LogoutRes res = authUseCase.logout(tokenProvider.resolveToken(request), memberDetails.getUsername());
        return SuccessResponse.toResponseEntity(SuccessCode.LOGOUT_SUCCESS, res);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request,
                                     @AuthenticationPrincipal MemberDetails memberDetails) {
        AuthRes.TokenRes res = authUseCase.createNewAccessToken(tokenProvider.resolveToken(request), memberDetails.getUsername());
        return SuccessResponse.toResponseEntity(SuccessCode.REISSUE_SUCCESS, res);
    }
}
