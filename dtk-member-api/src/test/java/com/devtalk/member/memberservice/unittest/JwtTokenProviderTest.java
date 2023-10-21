package com.devtalk.member.memberservice.unittest;

import com.devtalk.member.memberservice.global.security.JwtProperties;
import com.devtalk.member.memberservice.global.security.TokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private JwtProperties jwtProperties;

    @Test
    void generateAccessToken() {
        // given: 테스트 유저
        // when: generateAccessToken()
        // then: 토큰 복호화 -> claim에 넣어둔 email 확인
    }
}
