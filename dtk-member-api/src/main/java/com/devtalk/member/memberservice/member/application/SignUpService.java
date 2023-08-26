package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.member.application.port.in.SignUpUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.SignUpReq;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
import com.devtalk.member.memberservice.member.application.validator.SignUpValidator;
import com.devtalk.member.memberservice.member.domain.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.devtalk.member.memberservice.member.domain.member.Member.createMember;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService implements SignUpUseCase {

    private final MemberRepo memberRepo;
    private final SignUpValidator signUpValidator;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpReq req) {
        signUpValidator.validate(req);
        Member member = createMember(req, passwordEncoder);
        memberRepo.save(member);
    }

    @Override
    public boolean checkDuplicatedEmail(String email) {
        return memberRepo.existsByEmail(email);
    }

}
