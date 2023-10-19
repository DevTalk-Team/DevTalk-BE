package com.devtalk.member.memberservice.global.security;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.TokenException;
import com.devtalk.member.memberservice.global.util.RedisUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
@RefreshScope
public class JwtTokenProvider implements InitializingBean {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";

    private final MemberDetailsService memberDetailsService;
    private final RedisUtil redisUtil;
    private final String secretKey;
    private final Long accessTokenValidity;
    private final Long refreshTokenValidity;

    private Key key;

    public JwtTokenProvider(@Value("${jwt.token.secret}") String secretKey,
                            @Value("${jwt.token.access-token-validity-in-seconds}") Long accessTokenValidity,
                            @Value("${jwt.token.refresh-token-validity-in-seconds}") Long refreshTokenValidity,
                            MemberDetailsService memberDetailsService, RedisUtil redisUtil) {
        this.secretKey = secretKey;
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
//        this.accessTokenValidity = 180L;
//        this.refreshTokenValidity = 240L;
        this.memberDetailsService = memberDetailsService;
        this.redisUtil = redisUtil;
    }

    @Override
    public void afterPropertiesSet() throws Exception { // init(), 시크릿 키 설정
        log.info("[afterPropertiesSet] key 초기화 시작");
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        key = Keys.hmacShaKeyFor(encodedKey.getBytes());
        log.info("[afterPropertiesSet] key 초기화 완료");
    }

    @Transactional
    public String generateToken(Authentication authentication, Long tokenValidity) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        log.info("[createToken] 토큰 생성 시작");
        Claims claims = Jwts.claims();
        claims.put("email", authentication.getName());
        claims.put("auth", authorities);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, accessTokenValidity);
    }

    public String generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, refreshTokenValidity);
    }

    /* 토큰으로부터 정보 추출 */
    public Authentication getAuthentication(String token) {
        log.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        MemberDetails memberDetails = memberDetailsService.loadUserByUsername(getEmail(token));
        log.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails email : {}", memberDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(memberDetails, token, memberDetails.getAuthorities());
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

    public Long getExpiration(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        Long now = new Date().getTime();
        return expiration.getTime() - now;
    }

    /* 토큰 검증 */
    public boolean validateToken(String token) {
        log.info("[validateToken] 토큰 유효 체크 시작");
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
            log.info("[validateToken] return 값 = {}", !claims.getBody().getExpiration().before(new Date()));
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

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getRedisRefreshToken(String email) {
        String refreshToken = redisUtil.getData(email);
        if (refreshToken == null) {
            throw new TokenException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
        return refreshToken;
    }
}
