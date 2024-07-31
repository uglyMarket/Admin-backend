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

    // 필터의 주요 로직을 처리하는 메서드입니다.
    // 모든 HTTP 요청에 대해 JWT 토큰을 검증하고, 유효한 경우 이메일을 요청에 첨부합니다.
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // /login 및 /signup 경로는 필터를 거치지 않도록 설정
        if ("/api/admin/login".equals(path) || "/api/admin/register".equals(path)) {
            chain.doFilter(request, response);
            return;
        }

        // Authorization 헤더에서 JWT 토큰 추출
        String header = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // 헤더가 "Bearer "로 시작하는지 확인하고 토큰과 이메일을 추출
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            email = jwtUtil.getPhoneNumberFromToken(token);
        } else {
            // 헤더가 없거나 올바른 형식이 아닌 경우 적절한 응답을 설정
            setResponse(response, HttpServletResponse.SC_BAD_REQUEST, ErrorMsg.MISSING_AUTHORIZATION_HEADER);
            return;
        }

        if (email != null && jwtUtil.validateToken(token)) {
            // 이메일이 있고 토큰이 유효한 경우, 이메일을 요청에 추가합니다.
            request.setAttribute("email", email);
        } else if (header != null && header.startsWith("Bearer ")) {
            // 토큰이 만료되었거나 유효하지 않은 경우, 적절한 응답을 설정합니다.
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if (jwtUtil.validateToken(token)) {
                // Access Token이 만료된 경우
                response.getWriter().write(String.format("{\"message\": \"%s\"}", ErrorMsg.INVALID_TOKEN.getDetails()));
            } else {
                // Refresh Token을 사용하여 재인증 시도
                String refreshToken = request.getHeader("Refresh-Token");
                if (refreshToken != null && refreshToken.startsWith("Bearer ") && jwtUtil.validateToken(refreshToken.substring(7))) {
                    jwtUtil.refreshToken(request, response);
                } else {
                    // Refresh Token도 만료된 경우
                    setResponse(response, HttpServletResponse.SC_UNAUTHORIZED, ErrorMsg.INVALID_TOKEN);
                }
            }
            // 응답을 보낸 후, 더 이상 요청을 처리하지 않고 메서드를 끝냅니다.
            return;
        }

        // 다음 필터 또는 서블릿으로 요청을 전달
        chain.doFilter(request, response);
    }

    private void setResponse(HttpServletResponse response, int status, ErrorMsg errorMsg) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.format("{\"message\": \"%s\"}", errorMsg.getDetails()));
    }
}
