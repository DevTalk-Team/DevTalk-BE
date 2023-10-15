package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.security.MemberDetails;
import com.devtalk.member.memberservice.global.success.SuccessCode;
import com.devtalk.member.memberservice.global.success.SuccessResponse;
import com.devtalk.member.memberservice.global.success.SuccessResponseNoResult;
import com.devtalk.member.memberservice.member.application.port.in.MyPageUseCase;
import com.devtalk.member.memberservice.member.application.port.out.dto.MyPageRes;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "마이페이지", description = "비밀번호 수정, 마이페이지 정보 확인&수정")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MyPageApiController {
    private final MyPageUseCase myPageUseCase;

    @PostMapping("/mypage")
    public ResponseEntity<?> checkPassword(@RequestParam String checkPassword,
                                           @AuthenticationPrincipal MemberDetails memberDetails) {
        myPageUseCase.checkPassword(memberDetails.getUsername(), memberDetails.getPassword(), checkPassword);
        return SuccessResponseNoResult.toResponseEntity(SuccessCode.MYPAGE_PASSWORD_SUCCESS);
    }

    @GetMapping("/mypage")
    public ResponseEntity<?> getMyPage(@AuthenticationPrincipal MemberDetails memberDetails) {
        MyPageRes.InfoRes res = myPageUseCase.getMyPage(memberDetails.getUsername());
        return SuccessResponse.toResponseEntity(SuccessCode.MYPAGE_SUCCESS, res);
    }

    @PutMapping("/mypage")
    public ResponseEntity<?> updateMyPage(@AuthenticationPrincipal MemberDetails memberDetails) {

        return SuccessResponse.toResponseEntity(SuccessCode.MYPAGE_UPDATE_SUCCESS, "");
    }
}
