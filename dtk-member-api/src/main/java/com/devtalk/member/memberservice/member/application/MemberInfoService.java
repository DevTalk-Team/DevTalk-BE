package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.MemberNotFoundException;
import com.devtalk.member.memberservice.member.application.port.in.MemberInfoUseCase;
import com.devtalk.member.memberservice.member.application.port.out.dto.MemberRes;
import com.devtalk.member.memberservice.member.application.port.out.repository.ConsultantInfoRepo;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
import com.devtalk.member.memberservice.member.domain.consultation.ConsultantInfo;
import com.devtalk.member.memberservice.member.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements MemberInfoUseCase {
    private final MemberRepo memberRepo;
    private final ConsultantInfoRepo consultantInfoRepo;

    @Override
    public MemberRes.ConsultantRes findConsultantById(Long consultant) {
        Member member = memberRepo.findById(consultant)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        ConsultantInfo info = consultantInfoRepo.findByMember(member);
        return MemberRes.ConsultantRes.of(member, info.getF2f1h(), info.getNf2f1h());
    }

    @Override
    public MemberRes.ConsulterRes findConsulterById(Long consulter) {
        Member member = memberRepo.findById(consulter)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        return MemberRes.ConsulterRes.of(member);
    }

    @Override
    public MemberRes.ProfileRes findMemberByEmail(String email) {
        Member member = memberRepo.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        return MemberRes.ProfileRes.of(member);
    }
}
