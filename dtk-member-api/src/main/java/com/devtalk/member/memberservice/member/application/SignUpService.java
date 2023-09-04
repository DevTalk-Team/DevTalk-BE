package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.member.application.port.in.SignUpUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.SignUpReq;
import com.devtalk.member.memberservice.member.application.port.out.repository.CategoryRepo;
import com.devtalk.member.memberservice.member.application.port.out.repository.ConsultantInfoRepo;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberCategoryRepo;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
import com.devtalk.member.memberservice.member.application.validator.SignUpValidator;
import com.devtalk.member.memberservice.member.domain.category.Category;
import com.devtalk.member.memberservice.member.domain.category.MemberCategory;
import com.devtalk.member.memberservice.member.domain.consultation.ConsultantInfo;
import com.devtalk.member.memberservice.member.domain.member.Member;
import com.devtalk.member.memberservice.member.domain.member.MemberType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.devtalk.member.memberservice.member.domain.member.Member.createMember;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService implements SignUpUseCase {

    private final MemberRepo memberRepo;
    private final SignUpValidator signUpValidator;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepo categoryRepo;
    private final MemberCategoryRepo memberCategoryRepo;
    private final ConsultantInfoRepo consultantInfoRepo;

    @Override
    public Member signUp(SignUpReq req) {
        signUpValidator.validate(req);
        Member member = createMember(req, passwordEncoder);
        Member savedMember = memberRepo.save(member);
        toMemberCategory(member, req.getCategory());

        if(req.getMemberType() == MemberType.CONSULTANT) {
            setConsultantInfo(member);
        }

        return savedMember;
    }

    private void setConsultantInfo(Member member) {
        log.info("consultant info 생성 시작");
        ConsultantInfo consultantInfo = ConsultantInfo.setMember(member);
        log.info("consultant info 생성 완료 {}", consultantInfo);
        consultantInfoRepo.save(consultantInfo);
    }

    private void toMemberCategory(Member member, List<String> category) {
        for (String s : category) {
            Category findCategory = categoryRepo.findByCategory(s);
            MemberCategory memberCategory = MemberCategory.createMemberCategory(member, findCategory);
            memberCategoryRepo.save(memberCategory);
        }
    }

    @Override
    public boolean checkDuplicatedEmail(String email) {
        return memberRepo.existsByEmail(email);
    }

}
