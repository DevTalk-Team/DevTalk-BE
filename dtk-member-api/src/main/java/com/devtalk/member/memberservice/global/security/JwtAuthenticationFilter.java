package com.devtalk.member.memberservice.global.security;

import com.devtalk.member.memberservice.global.error.ErrorCode;
import com.devtalk.member.memberservice.global.error.exception.TokenException;
import com.devtalk.member.memberservice.global.util.RedisUtil;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

// OncePerRequestFilter: 매 요청마다 체크 해주는 필터
// Jwt 토큰을 인증
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {
        try {
            // 1. 헤더에서 access Token 추출
            String accessToken = jwtTokenProvider.resolveToken(request);
            if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
                // 1.1 black token(로그아웃)인지 검사
                if (redisUtil.existData(accessToken)) {
                    String data = redisUtil.getData(accessToken);
                    if (data != null && data.equals("logout")) {
                        throw new TokenException(ErrorCode.LOGOUT_TOKEN);
                    }
                }
                // 2. 인증 객체 생성, Security Context에 인증 정보 저장
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (TokenException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그아웃된 토큰, 로그인 필요");
        }
        filterChain.doFilter(request, response);
    }
}
