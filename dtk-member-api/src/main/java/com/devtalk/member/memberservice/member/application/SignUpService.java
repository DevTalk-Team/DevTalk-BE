package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.DuplicationException;
import com.devtalk.member.memberservice.member.application.port.in.SignUpUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.MemberReq;
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
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.devtalk.member.memberservice.member.domain.member.Member.createMember;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignUpService implements SignUpUseCase {
    private final MemberRepo memberRepo;
    private final SignUpValidator signUpValidator;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepo categoryRepo;
    private final MemberCategoryRepo memberCategoryRepo;
    private final ConsultantInfoRepo consultantInfoRepo;

    @Transactional
    @Override
    public Member signUp(MemberReq.SignUpReq req) {
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
        ConsultantInfo consultantInfo = ConsultantInfo.setMember(member);
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
    public void checkDuplicatedEmail(String email) {
        if (memberRepo.existsByEmail(email)) {
            throw new DuplicationException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

}
