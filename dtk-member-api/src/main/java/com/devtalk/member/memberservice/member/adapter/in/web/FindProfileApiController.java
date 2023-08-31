package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.success.SuccessCode;
import com.devtalk.member.memberservice.global.success.SuccessResponse;
import com.devtalk.member.memberservice.global.success.SuccessResponseNoResult;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.ChangePasswordInput;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.FindEmailInput;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.SendPasswordInput;
import com.devtalk.member.memberservice.member.application.port.in.FindProfileUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "내 정보 찾기", description = "이메일 찾기, 임시 비밀번호 발급, 비밀번호 재설정")
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class FindProfileApiController {

    private final FindProfileUseCase findProfileUseCase;

    @PostMapping("/find-email")
    public SuccessResponse<String> findEmail(@RequestBody FindEmailInput input) {
        String findEmail = findProfileUseCase.findEmail(input.getName(), input.getPhoneNumber());
        return new SuccessResponse<>(SuccessCode.FIND_EMAIL_SUCCESS, findEmail);
    }

    @GetMapping("/send-password")
    public SuccessResponseNoResult sendPassword(@RequestBody SendPasswordInput input) {
        findProfileUseCase.findPassword(input.getName(), input.getEmail());
        return new SuccessResponseNoResult(SuccessCode.FIND_EMAIL_SUCCESS);
    }

    @PostMapping("/change-password")
    public SuccessResponseNoResult changePassword(@RequestBody ChangePasswordInput input) {
        findProfileUseCase.changePassword(input.getPassword(), input.getNewPassword());
        return new SuccessResponseNoResult(SuccessCode.FIND_EMAIL_SUCCESS);
    }

}
