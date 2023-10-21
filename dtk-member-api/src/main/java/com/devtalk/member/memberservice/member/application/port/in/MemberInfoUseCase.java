package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantReq;
import com.devtalk.member.memberservice.member.application.port.out.dto.ConsultantRes;
import com.devtalk.member.memberservice.member.application.port.out.dto.MemberRes;
import com.devtalk.member.memberservice.member.domain.member.Member;

public interface MemberInfoUseCase {
    MemberRes.InfoRes findMemberById(Long memberId);
    MemberRes.ConsultantRes findConsultantById(Long consultant);

    MemberRes.ConsulterRes findConsulterById(Long consulter);
    MemberRes.ProfileRes findMemberByEmail(String email);
    Member findByEmail(String email);
}
