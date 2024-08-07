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

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        if (shouldSkipFilter(path)) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        String token = extractToken(header);

        if (token == null) {
            setResponse(response, HttpServletResponse.SC_BAD_REQUEST, ErrorMsg.MISSING_AUTHORIZATION_HEADER);
            return;
        }

        if (isValidToken(token)) {
            String email = jwtUtil.getPhoneNumberFromToken(token);
            request.setAttribute("email", email);
        } else {
            handleInvalidToken(request, response, token);
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean shouldSkipFilter(String path) {
        return "/api/admin/login".equals(path) || "/api/admin/register".equals(path);
    }

    private String extractToken(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private boolean isValidToken(String token) {
        String email = jwtUtil.getPhoneNumberFromToken(token);
        return email != null && jwtUtil.validateToken(token);
    }

    private void handleInvalidToken(HttpServletRequest request, HttpServletResponse response, String token) throws IOException {
        if (!jwtUtil.validateToken(token)) {
            tryRefreshToken(request, response);
        } else {
            setUnauthorizedResponse(response, ErrorMsg.INVALID_TOKEN);
        }
    }

    private void tryRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = request.getHeader("Refresh-Token");
        if (refreshToken != null && refreshToken.startsWith("Bearer ") && jwtUtil.validateToken(refreshToken.substring(7))) {
            tokenService.refreshToken(request, response);
        } else {
            setUnauthorizedResponse(response, ErrorMsg.INVALID_TOKEN);
        }
    }

    private void setUnauthorizedResponse(HttpServletResponse response, ErrorMsg errorMsg) throws IOException {
        setResponse(response, HttpServletResponse.SC_UNAUTHORIZED, errorMsg);
    }

    private void setResponse(HttpServletResponse response, int status, ErrorMsg errorMsg) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.format("{\"message\": \"%s\"}", errorMsg.getDetails()));
    }
}
