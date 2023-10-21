package com.devtalk.member.memberservice.global.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {
    @Value("${jwt.token.secret}")
    private String secret;
//    @Value("${jwt.token.access-token-validity-in-seconds}")
    private Long accessTokenValidity = 120L;
//    @Value("${jwt.token.refresh-token-validity-in-seconds}")
    private Long refreshTokenValidity = 180L;

}
