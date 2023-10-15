package com.devtalk.member.memberservice.global.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.devtalk.member.memberservice.global.error.ErrorCode.AUTH_FAIL;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 인증 실패
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("code", AUTH_FAIL.getCode());
        responseJson.put("message", AUTH_FAIL.getMessage());

        response.getWriter().print(responseJson);
    }
}
