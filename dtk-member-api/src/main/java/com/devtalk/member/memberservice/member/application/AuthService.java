package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.error.exception.MemberNotFoundException;
import com.devtalk.member.memberservice.global.error.exception.PasswordMismatchingException;
import com.devtalk.member.memberservice.global.util.JwtTokenUtil;
import com.devtalk.member.memberservice.member.application.port.in.AuthUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.LogInReq;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
import com.devtalk.member.memberservice.member.domain.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.devtalk.member.memberservice.global.error.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final MemberRepo memberRepo;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.token.secret}")
    private String secretKey;
    private long expiredTimeMs = 1000 * 60 * 60; // 1시간

    @Override
    public String login(LogInReq req) {
        Member findMember = memberRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));

        if(!passwordEncoder.matches(req.getPassword(), findMember.getPassword())) {
            throw new PasswordMismatchingException(WRONG_PASSWORD);
        }

        return JwtTokenUtil.createToken(req.getEmail(), secretKey, expiredTimeMs);
    }
}
