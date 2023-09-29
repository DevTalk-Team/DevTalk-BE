package com.devtalk.member.memberservice.member.application.port.in;

import com.devtalk.member.memberservice.member.application.port.out.dto.MemberRes;

public interface MemberInfoUseCase {
    MemberRes.ConsultantRes findConsultant(Long consultant);

    MemberRes.ConsulterRes findConsulter(Long consulter);
}
