package com.devtalk.member.memberservice.member.application.validator;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.JwtException;
import com.devtalk.member.memberservice.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthValidator {
    private final JwtTokenProvider jwtTokenProvider;

    public void reissueValidate(String refreshToken, String email) {
        String redisToken = jwtTokenProvider.getRedisRefreshToken(email); // RT 만료 -> LOGIN_REQUEST
        if (!Objects.equals(refreshToken, redisToken)) {
            throw new JwtException(ErrorCode.AUTH_FAIL); // 일치하지 않으면 AUTH_FAIL
        }
    }
}
