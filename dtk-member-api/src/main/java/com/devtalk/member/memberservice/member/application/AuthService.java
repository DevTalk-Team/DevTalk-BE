package com.devtalk.member.memberservice.member.application;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.TokenException;
import com.devtalk.member.memberservice.global.security.TokenProvider;
import com.devtalk.member.memberservice.global.util.RedisUtil;
import com.devtalk.member.memberservice.member.application.port.in.AuthUseCase;
import com.devtalk.member.memberservice.member.application.port.in.dto.AuthReq;
import com.devtalk.member.memberservice.member.application.port.out.dto.AuthRes;
import com.devtalk.member.memberservice.member.application.validator.AuthValidator;
import com.devtalk.member.memberservice.member.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements AuthUseCase {
    private final MemberInfoService memberInfoService;
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;
    private final AuthValidator validator;

    @Override
    public AuthRes.LoginRes login(AuthReq.LoginReq req) {
        Member member = memberInfoService.findByEmail(req.getEmail());

        validator.loginValidate(req, member);

        String accessToken = tokenProvider.generateAccessToken(member);

        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String refreshToken = tokenProvider.generateRefreshToken(member);

        return AuthRes.LoginRes.builder()
                .tokenType("Bearer ")
                .accessToken(accessToken)
                .email(member.getEmail())
                .refreshToken(refreshToken)
                .memberType(member.getMemberType().toString())
                .build();
    }

    @Override
    public AuthRes.LogoutRes logout(String accessToken, String userEmail) {
        tokenProvider.isValidToken(accessToken);

        String email = tokenProvider.getEmail(accessToken);
        redisUtil.deleteData(email);

        Long expiration = tokenProvider.getExpiration(accessToken);
        redisUtil.setDataExpire(accessToken, "logout", expiration);

        return AuthRes.LogoutRes
                .builder()
                .email(userEmail)
                .build();
    }

    @Override
    public AuthRes.TokenRes createNewAccessToken(String refreshToken, String email) {
        validator.reissueValidate(refreshToken, email);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new TokenException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        String newAccessToken = tokenProvider.generateAccessToken(memberInfoService.findByEmail(email));

        return AuthRes.TokenRes
                .builder()
                .accessToken(newAccessToken)
                .tokenType("Bearer ")
                .build();
    }

}
