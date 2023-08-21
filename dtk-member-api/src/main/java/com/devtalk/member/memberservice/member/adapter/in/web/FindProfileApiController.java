package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.success.SuccessCode;
import com.devtalk.member.memberservice.global.success.SuccessResponse;
import com.devtalk.member.memberservice.global.success.SuccessResponseNoResult;
import com.devtalk.member.memberservice.member.application.port.in.FindProfileUseCase;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class FindProfileApiController {

    private final FindProfileUseCase findProfileUseCase;

    @PostMapping("/find-email")
    public SuccessResponse<String> findEmail(@RequestParam String name, @RequestParam String phoneNumber) {
        String findEmail = findProfileUseCase.findEmail(name, phoneNumber);
        return new SuccessResponse<>(SuccessCode.FIND_EMAIL_SUCCESS, findEmail);
    }

    @GetMapping("/send-password")
    public SuccessResponseNoResult sendPassword(@RequestParam String name, @Email @RequestParam String email) {
        findProfileUseCase.findPassword(name, email);
        return new SuccessResponseNoResult(SuccessCode.FIND_EMAIL_SUCCESS);
    }

    @PostMapping("/change-password")
    public SuccessResponseNoResult changePassword(@RequestParam String password, @RequestParam String newPassword) {
        findProfileUseCase.changePassword(password, newPassword);
        return new SuccessResponseNoResult(SuccessCode.FIND_EMAIL_SUCCESS);
    }

}
