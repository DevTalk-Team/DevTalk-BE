package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.jwt.JwtTokenProvider;
import com.devtalk.member.memberservice.global.success.SuccessCode;
import com.devtalk.member.memberservice.global.success.SuccessResponse;
import com.devtalk.member.memberservice.global.success.SuccessResponseNoResult;
import com.devtalk.member.memberservice.member.application.port.in.MyPageUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.MyPageRes;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "마이페이지", description = "비밀번호 수정, 마이페이지 정보 확인&수정")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MyPageApiController {
    private final JwtTokenProvider jwtTokenProvider;
    private final MyPageUseCase myPageUseCase;

    @GetMapping("/mypage")
    public ResponseEntity<?> getMyPage(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        MyPageRes.InfoRes res = myPageUseCase.getMyPage(token);
        return SuccessResponse.toResponseEntity(SuccessCode.MYPAGE_SUCCESS, res);
    }

    @PostMapping("/mypage")
    public SuccessResponseNoResult checkPassword(HttpServletRequest request, @RequestParam String password) {
        return new SuccessResponseNoResult();
    }

    @PatchMapping("/mypage")
    public SuccessResponse<MyPageRes> editProfile(HttpServletRequest request) {
        return new SuccessResponse();
    }

    @GetMapping("/mypage/consultation")
    public SuccessResponse<MyPageRes> getMyConsultation(HttpServletRequest request) {
        return new SuccessResponse();
    }
}
