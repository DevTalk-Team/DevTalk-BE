package com.devtalk.member.memberservice.member.application.validator;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.MemberNotFoundException;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
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

}
