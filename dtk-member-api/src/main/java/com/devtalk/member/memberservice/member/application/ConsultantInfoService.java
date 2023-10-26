package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.MemberNotFoundException;
import com.devtalk.member.memberservice.global.vo.BaseFile;
import com.devtalk.member.memberservice.member.adapter.in.web.dto.ConsultantInput;
import com.devtalk.member.memberservice.member.application.port.in.ConsultantInfoUseCase;
import com.devtalk.member.memberservice.member.application.port.in.FileUploadUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.ConsultantReq;
import com.devtalk.member.memberservice.member.application.port.out.dto.ConsultantRes;
import com.devtalk.member.memberservice.member.application.port.out.repository.*;
import com.devtalk.member.memberservice.member.domain.category.Category;
import com.devtalk.member.memberservice.member.domain.category.MemberCategory;
import com.devtalk.member.memberservice.member.domain.consultation.ConsultantConsultationType;
import com.devtalk.member.memberservice.member.domain.consultation.ConsultantInfo;
import com.devtalk.member.memberservice.member.domain.consultation.ConsultationType;
import com.devtalk.member.memberservice.member.domain.consultation.ProfileImage;
import com.devtalk.member.memberservice.member.domain.member.Member;
import com.devtalk.member.memberservice.member.domain.region.MemberRegion;
import com.devtalk.member.memberservice.member.domain.region.Region;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConsultantInfoService implements ConsultantInfoUseCase {
    private final MemberRepo memberRepo;
    private final ConsultantInfoRepo consultantInfoRepo;
    private final ConsultationTypeRepo consultationTypeRepo;
    private final ConsultantConsultationTypeRepo consultantConsultationTypeRepo;
    private final CategoryRepo categoryRepo;
    private final MemberCategoryRepo memberCategoryRepo;
    private final RegionRepo regionRepo;
    private final MemberRegionRepo memberRegionRepo;
    private final MemberQueryableRepo memberQueryableRepo;

    private final FileUploadUseCase fileUploadUseCase;
    private final ProfileImageRepo profileImageRepo;

    @Override
    public ConsultantRes.InfoRes getInfo(String email) {
        Member member = memberRepo.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        ConsultantInfo info = consultantInfoRepo.findByMember(member);
        return ConsultantRes.InfoRes.of(info, fileUploadUseCase.getConsultantInfoFileList(info.getId()));
    }

    @Transactional
    @Override
    public ConsultantRes.InfoRes updateInfo(String email, ConsultantReq.InfoReq req) {
        Member member = memberRepo.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        ConsultantInfo info = consultantInfoRepo.findByMember(member);

        ConsultantInfo newInfo = info.update(req);
        if (req.getProfileFileResList() != null) {
            uploadProfileFileList(newInfo, req.getProfileFileResList());
        }
        return ConsultantRes.InfoRes.of(consultantInfoRepo.save(newInfo), fileUploadUseCase.getConsultantInfoFileList(info.getId()));
    }

    private void uploadProfileFileList(ConsultantInfo info, List<MultipartFile> profileFileList) {
        fileUploadUseCase.deleteConsultantInfoFileList(info.getId());
        List<BaseFile> baseFiles = fileUploadUseCase.uploadConsultantInfoFileList(profileFileList);

        baseFiles.forEach(baseFile ->
                profileImageRepo.save(
                        ProfileImage.createProfileImage(
                                info,
                                baseFile.getFileUrl(),
                                baseFile.getOriginFileName(),
                                baseFile.getStoredFileName())
                ));
    }

    @Override
    public List<String> getType(String email) {
        Member member = memberRepo.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        List<ConsultantConsultationType> types = consultantConsultationTypeRepo.findAllByMemberId(member.getId());
        List<String> result = new ArrayList<>();
        types.forEach((type) -> result.add(type.getConsultationType().getConsultationType()));
        return result;
    }

    @Transactional
    @Override
    public void updateType(String email, ConsultantInput.ListInput input) {
        Member member = memberRepo.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
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
    public List<String> getCategory(String email) {
        Member member = memberRepo.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        List<MemberCategory> categories = memberCategoryRepo.findAllByMemberId(member.getId());
        List<String> result = new ArrayList<>();
        categories.forEach((category) -> result.add(category.getCategory().getCategory()));
        return result;
    }

    @Transactional
    @Override
    public void updateCategory(String email, ConsultantInput.ListInput input) {
        Member member = memberRepo.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
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
    public List<String> getRegion(String email) {
        Member member = memberRepo.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        List<MemberRegion> regions = memberRegionRepo.findAllByMemberId(member.getId());
        List<String> result = new ArrayList<>();
        regions.forEach((region) -> result.add(region.getRegion().getRegion()));
        return result;
    }

    @Transactional
    @Override
    public void updateRegion(String email, ConsultantInput.ListInput input) {
        Member member = memberRepo.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        if (memberRegionRepo.existsByMemberId(member.getId())) {
            memberRegionRepo.deleteAllByMemberId(member.getId());
        }
        for (String s : input.getList()) {
            Region findRegion = regionRepo.findByRegion(s);
            MemberRegion memberRegion = MemberRegion.createMemberRegion(member, findRegion);
            memberRegionRepo.save(memberRegion);
        }
    }

    @Override
    public List<ConsultantRes.ConsultationRes> findConsultantForConsultation(ConsultantReq.ConsultationReq req) {
        // 1. 상담 분야 type
        Long typeId = consultationTypeRepo.findByConsultationType(req.getType()).getId();
        // 2. 기술 분야 category
        Long categoryId = categoryRepo.findByCategory(req.getCategory()).getId();
        // 3. 대면 여부 f2f
        if (req.getF2f() == "") { // 비대면 or 게시판 상담
            return memberQueryableRepo.findNf2fConsultant(typeId, categoryId);
        }
        // 3.1 지역 region
        Long regionId = regionRepo.findByRegion(req.getRegion()).getId(); // 대면
        return memberQueryableRepo.findF2fConsultant(typeId, categoryId, regionId);
    }
}
