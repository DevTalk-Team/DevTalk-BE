package com.devtalk.member.memberservice.member.application.validator;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.MemberNotFoundException;
import com.devtalk.member.memberservice.global.util.RedisUtil;
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
    private final RedisUtil redisUtil;

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
        String email = redisUtil.getData(password);
        return memberRepo.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
