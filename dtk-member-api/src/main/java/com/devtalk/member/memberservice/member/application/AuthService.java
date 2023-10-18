package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.JwtException;
import com.devtalk.member.memberservice.global.security.JwtTokenProvider;
import com.devtalk.member.memberservice.global.security.MemberDetails;
import com.devtalk.member.memberservice.global.util.RedisUtil;
import com.devtalk.member.memberservice.member.application.port.in.AuthUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.AuthReq;
import com.devtalk.member.memberservice.member.application.port.out.dto.AuthRes;
import com.devtalk.member.memberservice.member.application.validator.AuthValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisUtil redisUtil;
    private final AuthValidator validator;

    @Override
    public AuthRes.LoginRes login(AuthReq.LogInReq req) {
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

        log.info("[login] LoginRes 객체 생성");

        AuthRes.LoginRes logInRes = AuthRes.LoginRes.builder()
                .accessToken(accessToken)
                .tokenType("Bearer ")
                .email(member.getUsername())
                .refreshToken(refreshToken)
                .build();

        return logInRes;
    }

    @Override
    public AuthRes.LogoutRes logout(String accessToken, String userEmail) {
        // access token 유효성 확인
        jwtTokenProvider.validateToken(accessToken);

        // 로그인한 유저의 email와 access token의 email와 일치하는지 확인 후,
        // redis에 저장된 유저의 refresh token 삭제
        String email = jwtTokenProvider.getEmail(accessToken);
        redisUtil.deleteData(email);
        // access token을 black list에 현재 남은 유효 기간만큼 저장,
        // (accessToken, "logout")
        Long expiration = jwtTokenProvider.getExpiration(accessToken);
        redisUtil.setDataExpire(accessToken, "logout", expiration);
        return AuthRes.LogoutRes
                .builder()
                .email(userEmail)
                .build();
    }

    @Override
    public AuthRes.TokenRes reissueAccessToken(String refreshToken, String email) {
        log.info("[reissue 시작");
        validator.reissueValidate(refreshToken, email);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new JwtException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);
        log.info("[newAccessToken = {}", newAccessToken);
        return AuthRes.TokenRes
                .builder()
                .accessToken(newAccessToken)
                .tokenType("Bearer ")
                .build();
    }

}
