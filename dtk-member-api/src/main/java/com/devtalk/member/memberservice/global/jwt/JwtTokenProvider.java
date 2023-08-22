package com.devtalk.member.memberservice.global.jwt;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.TokenException;
import com.devtalk.member.memberservice.member.application.port.in.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider implements InitializingBean {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";

    private final MemberDetailsService memberDetailsService;
    private final String secretKey;
    private final long accessTokenValidity;
    private final long refreshTokenValidity;

    private Key key;

    public JwtTokenProvider(@Value("${jwt.token.secret}") String secretKey,
                            @Value("${jwt.token.access-token-validity-in-seconds}") long accessTokenValidity,
                            @Value("${jwt.token.refresh-token-validity-in-seconds}") long refreshTokenValidity,
                            MemberDetailsService memberDetailsService) {
        this.secretKey = secretKey;
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
        this.memberDetailsService = memberDetailsService;
    }

    @Override
    public void afterPropertiesSet() throws Exception { // init(), 시크릿 키 설정
        log.info("[afterPropertiesSet] key 초기화 시작");
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        key = Keys.hmacShaKeyFor(encodedKey.getBytes());
        log.info("[afterPropertiesSet] key 초기화 완료");
    }

    /* 토큰 생성 */
    @Transactional
    public TokenDto createToken(String email, String authorities) {
        log.info("[createToken] 토큰 생성 시작");
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("authorities", authorities);
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setSubject("access-token")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidity))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject("refresh-token")
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidity))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        log.info("[createToken] 토큰 생성 완료");
        return new TokenDto(accessToken, refreshToken);
    }

    /* 토큰으로부터 정보 추출 */
    public Authentication getAuthentication(String token) {
        log.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        MemberDetails memberDetails = memberDetailsService.loadUserByUsername(getEmail(token));
        log.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails email : {}", memberDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(memberDetails, "", memberDetails.getAuthorities());
    }

    public String getEmail(String token) {
        log.info("[getEmail] 토큰 기반 회원 구별 정보 추출");
        String email = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .get("email").toString();
        log.info("[getEmail] 토큰 기반 회원 구별 정보 추출 완료, info : {}", email);
        return email;
    }

    public String resolveToken(HttpServletRequest request) {
        log.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
        return request.getHeader("X-AUTH-TOKEN");
    }

    /* 토큰 검증 */
    public boolean validateToken(String token) {
        log.info("[validateToken] 토큰 유효 체크 시작");
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

    // Filter에서 사용
//    public boolean validateAccessToken
}
