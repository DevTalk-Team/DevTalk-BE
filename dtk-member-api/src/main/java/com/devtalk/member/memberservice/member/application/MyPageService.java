package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.MemberNotFoundException;
import com.devtalk.member.memberservice.global.error.exception.PasswordMismatchingException;
import com.devtalk.member.memberservice.member.application.port.in.MyPageUseCase;
import com.devtalk.member.memberservice.member.application.port.out.dto.MyPageRes;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberCategoryRepo;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
import com.devtalk.member.memberservice.member.domain.category.MemberCategory;
import com.devtalk.member.memberservice.member.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService implements MyPageUseCase {
    private final MemberRepo memberRepo;
    private final MemberCategoryRepo memberCategoryRepo;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public void checkPassword(String email, String password, String checkPassword) {
        if (!passwordEncoder.matches(checkPassword, password)) {
            throw new PasswordMismatchingException(ErrorCode.PASSWORD_MISMATCHING);
        }
    }
}
