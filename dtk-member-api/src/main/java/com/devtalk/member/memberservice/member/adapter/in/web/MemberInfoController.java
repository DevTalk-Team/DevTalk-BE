package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.global.success.SuccessCode;
import com.devtalk.member.memberservice.global.success.SuccessResponse;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultantInput;
import com.devtalk.member.memberservice.member.application.port.in.MemberInfoUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantReq;
import com.devtalk.member.memberservice.member.application.port.out.dto.MemberRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member/profile")
@RequiredArgsConstructor
public class MemberInfoController {
    private final MemberInfoUseCase memberInfoUseCase;

    @GetMapping("/{memberId}")
    MemberRes.InfoRes findMember(@PathVariable Long memberId) {
        return memberInfoUseCase.findMemberById(memberId);
    }

    @GetMapping("/email/{email}")
    MemberRes.ProfileRes getMemberByEmail(@PathVariable String email) {
        return memberInfoUseCase.findMemberByEmail(email);
    }
    @GetMapping("/consultant/{consultant}")
    MemberRes.ConsultantRes getConsultantInfo(@PathVariable Long consultant) {
        return memberInfoUseCase.findConsultantById(consultant);
    }

    @GetMapping("/consulter/{consulter}")
    MemberRes.ConsulterRes getConsulterInfo(@PathVariable Long consulter) {
        return memberInfoUseCase.findConsulterById(consulter);
    }
}

