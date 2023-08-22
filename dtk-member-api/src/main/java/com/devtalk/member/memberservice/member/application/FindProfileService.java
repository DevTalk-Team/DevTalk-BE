package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.member.application.port.in.FindProfileUseCase;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
import com.devtalk.member.memberservice.member.application.validator.FindProfileValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FindProfileService implements FindProfileUseCase {

    private final MemberRepo memberRepo;
    private final FindProfileValidator validator;

    @Override
    public String findEmail(String name, String phoneNumber) {
        validator.findEmailValidate(name, phoneNumber);
        return memberRepo.findEmailByNameAndPhoneNumber(name, phoneNumber);
    }

    @Override
    public void findPassword(String name, String email) {

    }

    @Override
    public void changePassword(String password, String newPassword) {

    }
}
