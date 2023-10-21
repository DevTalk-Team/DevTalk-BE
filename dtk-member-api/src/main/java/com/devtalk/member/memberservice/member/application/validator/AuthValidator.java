package com.devtalk.member.memberservice.member.application.validator;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.TokenException;
import com.devtalk.member.memberservice.global.security.TokenProvider;
import com.devtalk.member.memberservice.global.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthValidator {
    private final RedisUtil redisUtil;

    public void reissueValidate(String refreshToken, String email) {
        String redisToken = getRedisRefreshToken(email); // RT 만료 -> LOGIN_REQUEST
        if (!Objects.equals(refreshToken, redisToken)) {
            throw new TokenException(ErrorCode.AUTH_FAIL); // 일치하지 않으면 AUTH_FAIL
        }
    }

    private String getRedisRefreshToken(String email) {
        String refreshToken = redisUtil.getData(email);
        if (refreshToken == null) {
            throw new TokenException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        return refreshToken;
    }
}
