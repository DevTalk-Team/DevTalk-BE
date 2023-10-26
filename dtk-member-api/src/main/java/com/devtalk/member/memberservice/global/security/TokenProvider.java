package com.devtalk.member.memberservice.global.security;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.TokenException;
import com.devtalk.member.memberservice.global.util.RedisUtil;
import com.devtalk.member.memberservice.member.domain.member.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RefreshScope
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {
    private final JwtProperties jwtProperties;
    private final MemberDetailsService memberDetailsService;
    private final RedisUtil redisUtil;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";
    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception { // init(), 시크릿 키 설정
        String encodedKey = Base64.getEncoder().encodeToString(jwtProperties.getSecret().getBytes());
        key = Keys.hmacShaKeyFor(encodedKey.getBytes());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    private String generateToken(Member member, Long expiredAt) {
//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));

        Claims claims = Jwts.claims();
        claims.put("email", member.getEmail());
//        claims.put("auth", authorities);

        return Jwts.builder()
                .setSubject(member.getName())
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiredAt * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Transactional
    public String generateAccessToken(Member member) {
        return generateToken(member, jwtProperties.getAccessTokenValidity());
    }

    @Transactional
    public String generateRefreshToken(Member member) {
        String refreshToken = generateToken(member, jwtProperties.getRefreshTokenValidity());
        redisUtil.setDataExpire(member.getEmail(), refreshToken, jwtProperties.getRefreshTokenValidity());
        return refreshToken;
    }

    /* 토큰 검증 */
    public boolean isValidToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (SignatureException e) {
            throw new TokenException(ErrorCode.INCORRECT_SIGNATURE);
        } catch (ExpiredJwtException e) {
            throw new TokenException(ErrorCode.EXPIRED_TOKEN);
        } catch (MalformedJwtException | UnsupportedJwtException e) {
            throw new TokenException(ErrorCode.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    public boolean isBlackToken(String accessToken) {
        if(redisUtil.existData(accessToken)) {
            return true;
        }
        return false;
    }

    /* 토큰으로부터 정보 추출 */
    public String getEmail(String token) {
        String email = Jwts.parser()// 클레임 조회
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .get("email").toString();
        return email;
    }

    public Long getExpiration(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        Long now = new Date().getTime();
        return expiration.getTime() - now;
    }

    public Authentication getAuthentication(String token) {
        MemberDetails memberDetails = memberDetailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(memberDetails, token, memberDetails.getAuthorities());
    }
}
