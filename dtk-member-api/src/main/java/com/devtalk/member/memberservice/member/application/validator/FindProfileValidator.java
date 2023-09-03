package com.devtalk.member.memberservice.member.application.validator;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.MemberNotFoundException;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
import com.devtalk.member.memberservice.member.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindProfileValidator {
    private final MemberRepo memberRepo;

    public void findEmailValidate(String name, String phoneNumber) {
        if(!memberRepo.existsByNameAndPhoneNumber(name, phoneNumber)) {
            throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }

    public Member sendTempPasswordValidate(String name, String email) {
        return memberRepo.findByNameAndEmail(name, email)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Member changePasswordValidate(String password) {
        return null;
    }
}
