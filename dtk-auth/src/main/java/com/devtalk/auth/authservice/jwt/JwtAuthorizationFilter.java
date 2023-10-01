package com.devtalk.auth.authservice.jwt;

import com.devtalk.auth.authservice.error.ErrorCode;
import com.devtalk.auth.authservice.error.exception.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private String secretKey = "c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";
    private Key KEY;
    private final MemberDetailsService memberDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("[doFilterInternal] 진입");
        String header = request.getHeader("Authorization");
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        KEY = Keys.hmacShaKeyFor(encodedKey.getBytes());
        log.info("KEY 생성");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            log.info("헤더 없음");
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        log.info("Authentication 객체 생성 완료");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("setAuthentication 성공");
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = resolveToken(request);

        if (token != null && validateToken(token)) {
            String email = getEmail(token);
            log.info("user-email = {}", email);

            if (email != null) {
                MemberDetails memberDetails = memberDetailsService.loadUserByUsername(email);
                return new UsernamePasswordAuthenticationToken(memberDetails, token, memberDetails.getAuthorities());
            }

            return null;
        }

        return null;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(KEY)
                    .parseClaimsJws(token);
            log.info("token 유효성 검증");
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

    private String getEmail(String token) {
        return Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("email")
                .toString();
    }
}