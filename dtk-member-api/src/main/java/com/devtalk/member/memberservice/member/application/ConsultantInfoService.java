package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.MemberNotFoundException;
import com.devtalk.member.memberservice.global.jwt.JwtTokenProvider;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultantInfoInput;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultationCategoryInput;
import com.devtalk.member.memberservice.member.application.port.in.ConsultantInfoUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantInfoRes;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantTypeRes;
import com.devtalk.member.memberservice.member.application.port.out.repository.*;
import com.devtalk.member.memberservice.member.domain.category.Category;
import com.devtalk.member.memberservice.member.domain.category.MemberCategory;
import com.devtalk.member.memberservice.member.domain.consultation.ConsultantInfo;
import com.devtalk.member.memberservice.member.domain.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ConsultantInfoService implements ConsultantInfoUseCase {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepo memberRepo;
    private final ConsultantInfoRepo consultantInfoRepo;
    private final MemberCategoryQueryableRepo memberCategoryQueryableRepo;
    private final CategoryRepo categoryRepo;
    private final MemberCategoryRepo memberCategoryRepo;

    private Member getMember(String token) {
        String email = jwtTokenProvider.getEmail(token);
        Member member = memberRepo.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        return member;
    }

    @Override
    public ConsultantInfoRes getInfo(String token) {
        Member member = getMember(token);
        ConsultantInfo info = consultantInfoRepo.findByMember(member);
        return ConsultantInfoRes.toRes(info);
    }

    @Override
    public ConsultantInfoRes updateInfo(String token, ConsultantInfoInput input) {
        Member member = getMember(token);
        ConsultantInfo info = consultantInfoRepo.findByMember(member);
        return null;
    }

    @Override
    public List<String> getCategory(String token) {
        Member member = getMember(token);
        List<MemberCategory> categories = memberCategoryQueryableRepo.findAllById(member.getId());
        List<String> result = new ArrayList<>();
        categories.forEach((category) -> result.add(category.getCategory().getCategory()));
        return result;
    }

    @Override
    public ConsultantTypeRes updateType(String token, List<String> type) {
        return null;
    }

    @Override
    public void updateCategory(String token, ConsultationCategoryInput category) {
        Member member = getMember(token);
        memberCategoryQueryableRepo.deleteAllById(member.getId()); // 기존 분야 삭제
        for (String s : category.getList()) { // 다시 저장
            Category findCategory = categoryRepo.findByCategory(s);
            MemberCategory memberCategory = MemberCategory.createMemberCategory(member, findCategory);
            memberCategoryRepo.save(memberCategory);
        }
    }
}
