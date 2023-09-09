package com.devtalk.member.memberservice.member.application.validator;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.DuplicationException;
import com.devtalk.member.memberservice.global.error.exception.PasswordMismatchingException;
import com.devtalk.member.memberservice.member.application.port.in.dto.MemberReq;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpValidator {

    private final MemberRepo memberRepo;

    public void validate(MemberReq.SignUpReq req) {
        checkDuplicatedEmail(req.getEmail());
        checkPasswordMatching(req.getPassword(), req.getCheckPassword());
    }

    /* 이메일 중복 확인 */
    public void checkDuplicatedEmail(String email) {
        if (memberRepo.existsByEmail(email)) {
            throw new DuplicationException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    /* 비밀번호 일치 확인 */
    public void checkPasswordMatching(String password, String checkPassword) {
        if (!password.equals(checkPassword)) {
            throw new PasswordMismatchingException(ErrorCode.PASSWORD_MISMATCHING);
        }
    }
}
