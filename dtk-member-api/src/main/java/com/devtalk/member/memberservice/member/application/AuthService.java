package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.error.exception.MemberNotFoundException;
import com.devtalk.member.memberservice.global.error.exception.PasswordMismatchingException;
import com.devtalk.member.memberservice.global.jwt.JwtTokenProvider;
import com.devtalk.member.memberservice.member.application.port.in.AuthUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.LogInReq;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
import com.devtalk.member.memberservice.member.domain.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.devtalk.member.memberservice.global.error.ErrorCode.MEMBER_NOT_FOUND;
import static com.devtalk.member.memberservice.global.error.ErrorCode.WRONG_PASSWORD;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final MemberRepo memberRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LogInReq req) {
        Member findMember = memberRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        if(!passwordEncoder.matches(req.getPassword(), findMember.getPassword())) {
            throw new PasswordMismatchingException(WRONG_PASSWORD);
        }

        String accessToken = jwtTokenProvider.generateToken(req.getEmail());

        return accessToken;
    }

    @Override
    public void logout() {

    }
}
