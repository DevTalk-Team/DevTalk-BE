package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.MemberNotFoundException;
import com.devtalk.member.memberservice.global.jwt.JwtTokenProvider;
import com.devtalk.member.memberservice.member.application.port.in.ConsultantInfoUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantCategoryDto;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantInfoRes;
import com.devtalk.member.memberservice.member.application.port.out.repository.ConsultantInfoRepo;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
import com.devtalk.member.memberservice.member.domain.consultation.ConsultantInfo;
import com.devtalk.member.memberservice.member.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultantInfoService implements ConsultantInfoUseCase {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepo memberRepo;
    private final ConsultantInfoRepo consultantInfoRepo;

    @Override
    public ConsultantInfoRes getInfo(String token) {
        String email = jwtTokenProvider.getEmail(token);
        Member member = memberRepo.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        ConsultantInfo info = consultantInfoRepo.findByMember(member);
        return ConsultantInfoRes.toRes(info);
    }

    @Override
    public ConsultantCategoryDto getCategory() {
        return null;
    }
}
