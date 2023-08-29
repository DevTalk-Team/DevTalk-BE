package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.MemberNotFoundException;
import com.devtalk.member.memberservice.member.application.port.in.SignUpUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.SignUpReq;
import com.devtalk.member.memberservice.member.application.port.out.repository.CategoryRepo;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberCategoryRepo;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
import com.devtalk.member.memberservice.member.application.validator.SignUpValidator;
import com.devtalk.member.memberservice.member.domain.category.Category;
import com.devtalk.member.memberservice.member.domain.category.MemberCategory;
import com.devtalk.member.memberservice.member.domain.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.devtalk.member.memberservice.member.domain.member.Member.createMember;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService implements SignUpUseCase {

    private final MemberRepo memberRepo;
    private final SignUpValidator signUpValidator;
    private final PasswordEncoder passwordEncoder;

    private final CategoryRepo categoryRepo;
    private final MemberCategoryRepo memberCategoryRepo;

    @Override
    public void signUp(SignUpReq req) {
        signUpValidator.validate(req);
        Member member = createMember(req, passwordEncoder);
        memberRepo.save(member);
        toMemberCategory(member, req.getCategory());
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
