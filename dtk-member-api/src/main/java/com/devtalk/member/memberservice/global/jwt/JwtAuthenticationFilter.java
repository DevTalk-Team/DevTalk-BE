package com.devtalk.member.memberservice.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.http.HttpHeaders;

// OncePerRequestFilter: 매 요청마다 체크 해주는 필터
// Jwt 토큰을 인증
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 인증이 필요한 요청이 오면 헤더에서 AccessToken을 추출하고 정상 토큰인지 검사한다.

        String accessToken = resolveToken(request);
        log.info("[doFilterInternal] access token 값 추출 완료. access token : {}", accessToken);

        log.info("[doFilterInternal] access token 값 유효성 체크 시작");
        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication); // Security Context에 인증 정보 저장
            log.info("[doFilterInternal] access token 값 유효성 체크 완료");
        } else if (accessToken != null && !jwtTokenProvider.validateToken(accessToken)) {
            log.info("[doFilterInternal] access token 값 만료");

            String refreshToken = null;
            if (request.getHeader("Auth") != null) {
                String email = request.getHeader("Auth");
                refreshToken = jwtTokenProvider.getRefreshToken(email);
                log.info("[doFilterInternal] refresh token 값 찾기");
            }

            log.info("[doFilterInternal] refresh token 값 유효성 체크 시작");
            if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
                log.info("[doFilterInternal] access token 값 재발급 시작");
                Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
                String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                response.setHeader("Authorization", newAccessToken);
                log.info("[doFilterInternal] access token 값 재발급 완료");
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
