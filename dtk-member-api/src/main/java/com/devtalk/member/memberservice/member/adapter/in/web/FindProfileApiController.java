package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.success.SuccessResponse;
import com.devtalk.member.memberservice.global.success.SuccessResponseNoResult;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.FindProfileInput;
import com.devtalk.member.memberservice.member.adapter.out.web.dto.FindProfileOutput;
import com.devtalk.member.memberservice.member.application.port.in.FindProfileUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.devtalk.member.memberservice.global.success.SuccessCode.*;

@Tag(name = "내 정보 찾기", description = "이메일 찾기, 임시 비밀번호 발급, 비밀번호 재설정")
@RestController
@RequestMapping("/member/profile")
@RequiredArgsConstructor
public class FindProfileApiController {
    private final FindProfileUseCase findProfileUseCase;

    @Operation(summary = "내 정보 찾기 - 이메일 찾기", responses = {
            @ApiResponse(description = "성공", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @PostMapping("/find-email")
    public ResponseEntity<?> findEmail(@RequestBody FindProfileInput.EmailInput input) {
        String findEmail = findProfileUseCase.findEmail(input.getName(), input.getPhoneNumber());
        FindProfileOutput.EmailOutput output = new FindProfileOutput.EmailOutput(findEmail);
        return SuccessResponse.toResponseEntity(FIND_EMAIL_SUCCESS, output);
    }

    @Operation(summary = "내 정보 찾기 - 임시 비밀번호 발급", responses = {
            @ApiResponse(description = "성공", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseNoResult.class)))
    })
    @PostMapping("/send-password")
    public ResponseEntity<?> sendPassword(@RequestBody FindProfileInput.SendPasswordInput input) {
        findProfileUseCase.sendTempPassword(input.getName(), input.getEmail());
        return SuccessResponseNoResult.toResponseEntity(TEMP_PASSWORD_SUCCESS);
    }

    @Operation(summary = "내 정보 찾기 - 비밀번호 재설정", responses = {
            @ApiResponse(description = "성공", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseNoResult.class)))
    })
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody FindProfileInput.ChangePasswordInput input) {
        findProfileUseCase.changePassword(input.getPassword(), input.getNewPassword());
        return SuccessResponseNoResult.toResponseEntity(CHANGE_PASSWORD_SUCCESS);
    }
}
