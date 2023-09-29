package com.devtalk.member.memberservice.member.adapter.in.web;

import com.devtalk.member.memberservice.member.application.port.in.MemberInfoUseCase;
import com.devtalk.member.memberservice.member.application.port.out.dto.MemberRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MemberInfoController {
    private final MemberInfoUseCase memberInfoUseCase;

    @GetMapping("/member/profile/consultant/{consultant}")
    MemberRes.ConsultantRes getConsultantInfo(@PathVariable Long consultant) {
        return memberInfoUseCase.findConsultantById(consultant);
    }

    @GetMapping("/member/profile/consulter/{consulter}")
    MemberRes.ConsulterRes getConsulterInfo(@PathVariable Long consulter) {
        return memberInfoUseCase.findConsulterById(consulter);
    }

    @GetMapping("/member/profile/email/{email}")
    MemberRes.ProfileRes getMemberByEmail(@PathVariable String email) {
        return memberInfoUseCase.findMemberByEmail(email);
    }
}
