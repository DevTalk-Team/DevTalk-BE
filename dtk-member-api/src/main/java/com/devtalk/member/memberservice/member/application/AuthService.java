package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.jwt.JwtTokenProvider;
import com.devtalk.member.memberservice.global.jwt.MemberDetails;
import com.devtalk.member.memberservice.global.util.RedisUtil;
import com.devtalk.member.memberservice.member.application.port.in.AuthUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.AuthReq;
import com.devtalk.member.memberservice.member.application.port.out.dto.AuthRes;
import com.devtalk.member.memberservice.member.application.port.out.repository.MemberRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisUtil redisUtil;

    @Override
    public AuthRes.LogInRes login(AuthReq.LogInReq req) {
        // 1. Member 인증 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        MemberDetails member = (MemberDetails) authentication.getPrincipal(); // member 정보

        // 2. token 생성
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        // 3. refresh Token 저장
        redisUtil.setDataExpire(member.getUsername(), refreshToken, 1209600);

        log.info("[login] LogInRes 객체 생성");

        AuthRes.LogInRes logInRes = AuthRes.LogInRes.builder()
                .accessToken(accessToken)
                .tokenType("Bearer ")
                .email(member.getUsername())
                .refreshToken(refreshToken)
                .build();

        return logInRes;
    }

    @Override
    public void logout(String token) {
        // access token black list에 저장
        Long expiration = jwtTokenProvider.getExpiration(token);
        redisUtil.setDataExpire(token, "logout", expiration);
        // refresh token 삭제
        String email = jwtTokenProvider.getEmail(token);
        redisUtil.deleteData(email);
    }

}
