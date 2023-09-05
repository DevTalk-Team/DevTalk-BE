package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.success.SuccessResponse;
import com.devtalk.member.memberservice.global.success.SuccessResponseNoResult;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.MyPageOutput;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "마이페이지", description = "비밀번호 확인, 마이페이지 정보 확인&수정, 내 상담 확인")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyPageApiController {

    @PostMapping("/mypage")
    public SuccessResponseNoResult checkPassword(HttpServletRequest request, @RequestParam String password) {
        return new SuccessResponseNoResult();
    }

    @GetMapping("/mypage")
    public SuccessResponse<MyPageOutput> getMyPage(HttpServletRequest request) {
        return new SuccessResponse();
    }

    @PatchMapping("/mypage")
    public SuccessResponse<MyPageOutput> editProfile(HttpServletRequest request) {
        return new SuccessResponse();
    }

    @GetMapping("/mypage/consultation")
    public SuccessResponse<MyPageOutput> getMyConsultation(HttpServletRequest request) {
        return new SuccessResponse();
    }
}
