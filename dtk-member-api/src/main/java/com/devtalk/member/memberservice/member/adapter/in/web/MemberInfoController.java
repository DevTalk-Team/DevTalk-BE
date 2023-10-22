package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.member.application.port.in.MemberInfoUseCase;
import com.devtalk.member.memberservice.member.application.port.out.dto.MemberRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "feign client", description = "다른 서비스에서 사용될 멤버 정보 조회 Api")
@RestController
@RequestMapping("/member/profile")
@RequiredArgsConstructor
public class MemberInfoController {
    private final MemberInfoUseCase memberInfoUseCase;

    @Operation(summary = "feign client - memberId로 조회", responses = {
            @ApiResponse(description = "성공", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberRes.InfoRes.class)))
    })
    @GetMapping("/{memberId}")
    MemberRes.InfoRes findMember(@PathVariable Long memberId) {
        return memberInfoUseCase.findMemberById(memberId);
    }

    @Operation(summary = "feign client - email 조회", responses = {
            @ApiResponse(description = "성공", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberRes.ProfileRes.class)))
    })
    @GetMapping("/email/{email}")
    MemberRes.ProfileRes getMemberByEmail(@PathVariable String email) {
        return memberInfoUseCase.findMemberByEmail(email);
    }

    @Operation(summary = "feign client - memberId로 전문가 조회", responses = {
            @ApiResponse(description = "성공", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberRes.ConsultantRes.class)))
    })
    @GetMapping("/consultant/{consultant}")
    MemberRes.ConsultantRes getConsultantInfo(@PathVariable Long consultant) {
        return memberInfoUseCase.findConsultantById(consultant);
    }

    @Operation(summary = "feign client - memberId로 멘티 조회", responses = {
            @ApiResponse(description = "성공", responseCode = "200",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MemberRes.ConsulterRes.class)))
    })
    @GetMapping("/consulter/{consulter}")
    MemberRes.ConsulterRes getConsulterInfo(@PathVariable Long consulter) {
        return memberInfoUseCase.findConsulterById(consulter);
    }
}

