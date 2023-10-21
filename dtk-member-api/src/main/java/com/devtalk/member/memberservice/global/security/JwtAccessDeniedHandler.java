package com.devtalk.member.memberservice.global.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.devtalk.member.memberservice.global.error.ErrorCode.*;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    // 접근 막힘
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        JSONObject responseJson = new JSONObject();
        responseJson.put("code", MEMBER_NOT_FOUND.getCode());
        responseJson.put("message", MEMBER_NOT_FOUND.getMessage());

        response.getWriter().print(responseJson);
//        response.sendRedirect("/member/signup");
    }
}
