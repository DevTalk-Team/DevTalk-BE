package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.MemberNotFoundException;
import com.devtalk.member.memberservice.global.jwt.JwtTokenProvider;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultantInput;
import com.devtalk.member.memberservice.member.application.port.in.ConsultantInfoUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantRes;
import com.devtalk.member.memberservice.member.application.port.out.repository.*;
import com.devtalk.member.memberservice.member.domain.category.Category;
import com.devtalk.member.memberservice.member.domain.category.MemberCategory;
import com.devtalk.member.memberservice.member.domain.consultation.ConsultantConsultationType;
import com.devtalk.member.memberservice.member.domain.consultation.ConsultantInfo;
import com.devtalk.member.memberservice.member.domain.consultation.ConsultationType;
import com.devtalk.member.memberservice.member.domain.member.Member;
import com.devtalk.member.memberservice.member.domain.region.MemberRegion;
import com.devtalk.member.memberservice.member.domain.region.Region;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConsultantInfoService implements ConsultantInfoUseCase {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepo memberRepo;

    private final ConsultantInfoRepo consultantInfoRepo;

    private final ConsultationTypeRepo consultationTypeRepo;
    private final ConsultantConsultationTypeRepo consultantConsultationTypeRepo;

    private final CategoryRepo categoryRepo;
    private final MemberCategoryRepo memberCategoryRepo;

    private final RegionRepo regionRepo;
    private final MemberRegionRepo memberRegionRepo;

    private Member getMember(String token) {
        String email = jwtTokenProvider.getEmail(token);
        return memberRepo.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Override
    public ConsultantRes.InfoRes getInfo(String token) {
        Member member = getMember(token);
        ConsultantInfo info = consultantInfoRepo.findByMember(member);
        return ConsultantRes.InfoRes.of(info);
    }

    @Transactional
    @Override
    public ConsultantRes.InfoRes updateInfo(String token, ConsultantInput.InfoInput input) {
        Member member = getMember(token);
        ConsultantInfo info = consultantInfoRepo.findByMember(member);
        ConsultantInfo newInfo = info.update(input.toReq());
        return ConsultantRes.InfoRes.of(consultantInfoRepo.save(newInfo));
    }

    @Override
    public List<String> getType(String token) {
        Member member = getMember(token);
        List<ConsultantConsultationType> types = consultantConsultationTypeRepo.findAllByMemberId(member.getId());
        List<String> result = new ArrayList<>();
        types.forEach((type) -> result.add(type.getConsultationType().getConsultationType()));
        return result;
    }

    @Transactional
    @Override
    public void updateType(String token, ConsultantInput.ListInput input) {
        Member member = getMember(token);
        if (consultantConsultationTypeRepo.existsByMemberId(member.getId())) {
            consultantConsultationTypeRepo.deleteAllByMemberId(member.getId());
        }
        for (String s : input.getList()) {
            ConsultationType findType = consultationTypeRepo.findByConsultationType(s);
            ConsultantConsultationType consultantConsultationType = ConsultantConsultationType.createConsultantConsultationType(member, findType);
            consultantConsultationTypeRepo.save(consultantConsultationType);
        }
    }

    @Override
    public List<String> getCategory(String token) {
        Member member = getMember(token);
        List<MemberCategory> categories = memberCategoryRepo.findAllByMemberId(member.getId());
        List<String> result = new ArrayList<>();
        categories.forEach((category) -> result.add(category.getCategory().getCategory()));
        return result;
    }

    @Transactional
    @Override
    public void updateCategory(String token, ConsultantInput.ListInput input) {
        Member member = getMember(token);
        if (memberCategoryRepo.existsByMemberId(member.getId())) {
            memberCategoryRepo.deleteAllByMemberId(member.getId()); // 기존 분야 삭제
        }
        for (String s : input.getList()) { // 다시 저장
            Category findCategory = categoryRepo.findByCategory(s);
            MemberCategory memberCategory = MemberCategory.createMemberCategory(member, findCategory);
            memberCategoryRepo.save(memberCategory);
        }
    }

    @Override
    public List<String> getRegion(String token) {
        Member member = getMember(token);
        List<MemberRegion> regions = memberRegionRepo.findAllByMemberId(member.getId());
        List<String> result = new ArrayList<>();
        regions.forEach((region) -> result.add(region.getRegion().getRegion()));
        return result;
    }

    @Transactional
    @Override
    public void updateRegion(String token, ConsultantInput.ListInput input) {
        Member member = getMember(token);
        if (memberRegionRepo.existsByMemberId(member.getId())) {
            memberRegionRepo.deleteAllByMemberId(member.getId());
        }
        for (String s : input.getList()) {
            Region findRegion = regionRepo.findByRegion(s);
            MemberRegion memberRegion = MemberRegion.createMemberRegion(member, findRegion);
            memberRegionRepo.save(memberRegion);
        }
    }
}
