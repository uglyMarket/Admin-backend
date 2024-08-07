package com.sparta.uglymarket.filter;

import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.service.TokenService;
import com.sparta.uglymarket.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    // 필터의 주요 로직을 처리합니다.
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // 로그인 및 회원가입 경로는 필터를 거치지 않도록 설정
        if (shouldSkipFilter(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Authorization 헤더에서 JWT 토큰 추출
        String header = request.getHeader("Authorization");
        String token = extractToken(header);

        // 토큰이 없으면 400 응답
        if (token == null) {
            setResponse(response, HttpServletResponse.SC_BAD_REQUEST, ErrorMsg.MISSING_AUTHORIZATION_HEADER);
            return;
        }

        // 토큰이 유효하면 이메일을 요청에 첨부
        if (isValidToken(token)) {
            String email = jwtUtil.getPhoneNumberFromToken(token);
            request.setAttribute("email", email);
        } else {
            // 토큰이 유효하지 않으면 처리
            handleInvalidToken(request, response, token);
            return;
        }

        // 다음 필터 또는 서블릿으로 요청을 전달
        chain.doFilter(request, response);
    }

    // 로그인 및 회원가입 경로를 필터링에서 제외
    private boolean shouldSkipFilter(String path) {
        return "/api/admin/login".equals(path) || "/api/admin/register".equals(path);
    }

    // Authorization 헤더에서 토큰 추출
    private String extractToken(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    // 토큰이 유효한지 확인
    private boolean isValidToken(String token) {
        String email = jwtUtil.getPhoneNumberFromToken(token);
        return email != null && jwtUtil.validateToken(token);
    }

    // 유효하지 않은 토큰 처리
    private void handleInvalidToken(HttpServletRequest request, HttpServletResponse response, String token) throws IOException {
        if (!jwtUtil.validateToken(token)) {
            // 리프레시 토큰을 사용하여 재인증 시도
            tryRefreshToken(request, response);
        } else {
            // 유효하지 않은 토큰 응답 설정
            setUnauthorizedResponse(response, ErrorMsg.INVALID_TOKEN);
        }
    }

    // 리프레시 토큰을 사용하여 재인증 시도
    private void tryRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = request.getHeader("Refresh-Token");
        if (refreshToken != null && refreshToken.startsWith("Bearer ") && jwtUtil.validateToken(refreshToken.substring(7))) {
            tokenService.refreshToken(request, response);
        } else {
            // 리프레시 토큰도 유효하지 않은 경우 응답 설정
            setUnauthorizedResponse(response, ErrorMsg.INVALID_TOKEN);
        }
    }

    // 401 Unauthorized 응답 설정
    private void setUnauthorizedResponse(HttpServletResponse response, ErrorMsg errorMsg) throws IOException {
        setResponse(response, HttpServletResponse.SC_UNAUTHORIZED, errorMsg);
    }

    // 응답 설정
    private void setResponse(HttpServletResponse response, int status, ErrorMsg errorMsg) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.format("{\"message\": \"%s\"}", errorMsg.getDetails()));
    }
}
