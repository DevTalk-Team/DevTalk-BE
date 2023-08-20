package com.devtalk.member.memberservice.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secretKey;
    private long expiredTime = 60 * 60 * 1000; // 1시간

    public String generateToken(String email) {

        Claims claims = Jwts.claims();
        claims.put("email", email);

        Date now = new Date(System.currentTimeMillis());

        return Jwts.builder()
                .setSubject("authorization") // 토큰 용도
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiredTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

//    public Authentication getAuthentication(String token) {
//
//    }
}
