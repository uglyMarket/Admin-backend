package com.sparta.uglymarket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    // ObjectMapper를 주입받는 생성자
    public CustomAuthenticationFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // 인증 실패 시 호출되는 메소드
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        // 응답 상태를 401(Unauthorized)로 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 응답 콘텐츠 타입을 JSON으로 설정
        response.setContentType("application/json");

        // 오류 정보를 담을 맵 생성
        Map<String, String> data = new HashMap<>();
        data.put("status", "error");
        data.put("message", exception.getMessage());

        // JSON 형식으로 변환하여 응답에 작성
        response.getWriter().write(objectMapper.writeValueAsString(data));
    }
}
