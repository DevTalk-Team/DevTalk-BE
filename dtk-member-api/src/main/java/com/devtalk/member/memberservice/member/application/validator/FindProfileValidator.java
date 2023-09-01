package com.devtalk.member.memberservice.member.application.validator;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.MemberNotFoundException;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
import com.devtalk.member.memberservice.member.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FindProfileValidator {
    private final MemberRepo memberRepo;

    public void findEmailValidate(String name, String phoneNumber) {
        log.info("validate 시작");
        if(!memberRepo.existsByNameAndPhoneNumber(name, phoneNumber)) {
            log.info("validate 문제");
            throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        }
        log.info("validate 끝");
    }

    public Member sendTempPasswordValidate(String name, String email) {
        return memberRepo.findByNameAndEmail(name, email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Member changePasswordValidate(String password, String newPassword) {
        // 임시 비밀번호 발급 후 바로 페이지 넘어가면
        // 임시 비밀번호, email (key, value)로 redis에 넣어놓고
        // - 비밀번호가 redis에 없으면 throw exception
        // redis에서 password로 값 찾고 newPassword로 변경?!
        return null;
    }
}
