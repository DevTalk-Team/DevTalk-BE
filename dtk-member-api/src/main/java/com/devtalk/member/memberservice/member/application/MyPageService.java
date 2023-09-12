package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.MemberNotFoundException;
import com.devtalk.member.memberservice.global.jwt.JwtTokenProvider;
import com.devtalk.member.memberservice.member.application.port.in.MyPageUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.MyPageRes;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberCategoryRepo;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
import com.devtalk.member.memberservice.member.domain.category.Category;
import com.devtalk.member.memberservice.member.domain.category.MemberCategory;
import com.devtalk.member.memberservice.member.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService implements MyPageUseCase {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepo memberRepo;
    private final MemberCategoryRepo memberCategoryRepo;

    private Member getMember(String token) {
        String email = jwtTokenProvider.getEmail(token);
        Member member = memberRepo.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        return member;
    }

    @Override
    public MyPageRes.InfoRes getMyPage(String token) {
        Member member = getMember(token);
        List<MemberCategory> categories = memberCategoryRepo.findAllByMemberId(member.getId());
        List<String> result = new ArrayList<>();
        categories.forEach((category) -> result.add(category.getCategory().getCategory()));
        return MyPageRes.InfoRes.of(member, result);
    }
}
