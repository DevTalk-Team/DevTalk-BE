package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.MemberNotFoundException;
import com.devtalk.member.memberservice.global.error.exception.PasswordMismatchingException;
import com.devtalk.member.memberservice.member.application.port.in.MyPageUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.MyPageReq;
import com.devtalk.member.memberservice.member.application.port.out.dto.MyPageRes;
import com.devtalk.member.memberservice.member.application.port.out.repository.CategoryRepo;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberCategoryRepo;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
import com.devtalk.member.memberservice.member.domain.category.Category;
import com.devtalk.member.memberservice.member.domain.category.MemberCategory;
import com.devtalk.member.memberservice.member.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService implements MyPageUseCase {
    private final MemberRepo memberRepo;
    private final MemberCategoryRepo memberCategoryRepo;
    private final CategoryRepo categoryRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void checkPassword(String email, String password, String checkPassword) {
        if (!passwordEncoder.matches(checkPassword, password)) {
            throw new PasswordMismatchingException(ErrorCode.PASSWORD_MISMATCHING);
        }
    }

    private Member getMember(String email) {
        return memberRepo.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Override
    public MyPageRes.InfoRes getMyPage(String email) {
        Member member = getMember(email);

        List<MemberCategory> categories = memberCategoryRepo.findAllByMemberId(member.getId());
        List<String> result = new ArrayList<>();
        categories.forEach((category) -> result.add(category.getCategory().getCategory()));

        return MyPageRes.InfoRes.of(member, result);
    }

    @Transactional
    @Override
    public MyPageRes.UpdateRes updateMyPage(String email, MyPageReq.UpdateReq req) {
        Member member = getMember(email);
        member.updateNameAndPhoneNumber(req.getName(), req.getPhoneNumber());

        if (memberCategoryRepo.existsByMemberId(member.getId())) {
            memberCategoryRepo.deleteAllByMemberId(member.getId()); // 기존 분야 삭제
        }

        List<String> result = new ArrayList<>();
        for (String categoryStr : req.getCategories()) { // 다시 저장
            Category findCategory = categoryRepo.findByCategory(categoryStr);
            result.add(categoryStr);
            MemberCategory memberCategory = MemberCategory.createMemberCategory(member, findCategory);
            memberCategoryRepo.save(memberCategory);
        }

        return MyPageRes.UpdateRes.of(member, result);
    }
}
