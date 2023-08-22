package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.error.exception.MemberNotFoundException;
import com.devtalk.member.memberservice.global.error.exception.PasswordMismatchingException;
import com.devtalk.member.memberservice.global.jwt.JwtTokenProvider;
import com.devtalk.member.memberservice.member.application.port.in.AuthUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.AuthReq;
import com.devtalk.member.memberservice.member.application.port.in.dto.AuthRes;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
import com.devtalk.member.memberservice.member.domain.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.devtalk.member.memberservice.global.error.ErrorCode.MEMBER_NOT_FOUND;
import static com.devtalk.member.memberservice.global.error.ErrorCode.WRONG_PASSWORD;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {

    private final MemberRepo memberRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthRes.LogInRes login(AuthReq.LogInReq req) {
        // email 검증
        log.info("[login] email : {}", req.getEmail());
        Member findMember = memberRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
        // password 검증
        log.info("[login] 패스워드 비교 수행");
        if(!passwordEncoder.matches(req.getPassword(), findMember.getPassword())) {
            throw new PasswordMismatchingException(WRONG_PASSWORD);
        }
        log.info("[login] 패스워드 일치");

        log.info("[login] LogInRes 객체 생성");
        AuthRes.LogInRes logInRes = AuthRes.LogInRes.builder()
                .tokenDto(jwtTokenProvider.createToken(findMember.getEmail(), String.valueOf(findMember.getRoleType())))
                .build();

        return logInRes;
    }

    @Override
    public void logout() {

    }
}
