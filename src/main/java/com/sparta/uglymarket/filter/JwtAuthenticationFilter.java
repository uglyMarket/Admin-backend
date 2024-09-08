package com.sparta.uglymarket.filter;

import com.sparta.uglymarket.entity.AdminEntity;
import com.sparta.uglymarket.entity.Role;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.repository.AdminRepository;
import com.sparta.uglymarket.service.TokenService;
import com.sparta.uglymarket.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AdminRepository adminRepository;
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
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = extractToken(header);

        // 토큰이 없으면 400 응답
        if (token == null) {
            setResponse(response, ErrorMsg.MISSING_AUTHORIZATION_HEADER);
            return;
        }

        // 토큰이 유효하고 Admin 엔티티의 전화번호와 일치하는지 확인
        if (isValidToken(token)) {
            String phoneNumber = jwtUtil.getPhoneNumberFromToken(token);

            // Admin 엔티티에서 해당 전화번호로 사용자 조회
            Optional<AdminEntity> adminOptional = adminRepository.findByPhoneNumber(phoneNumber);
            if (adminOptional.isPresent()) {
                request.setAttribute("admin", adminOptional.get());
            } else {
                // 일치하는 Admin이 없으면 404 응답
                setResponse(response, ErrorMsg.PHONE_NUMBER_NOT_FOUND);
                return;
            }
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
        String phoneNumber = jwtUtil.getPhoneNumberFromToken(token);
        return phoneNumber != null && jwtUtil.validateToken(token);
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
        setResponse(response, errorMsg);
    }

    // 응답 설정 (ErrorMsg 기반)
    private void setResponse(HttpServletResponse response, ErrorMsg errorMsg) throws IOException {
        response.setStatus(errorMsg.getHttpStatus().value());  // 에러 메시지에 맞는 HTTP 상태 코드 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // JSON 형태로 응답
        response.getWriter().write(String.format("{\"message\": \"%s\"}", errorMsg.getDetails()));
    }
}
