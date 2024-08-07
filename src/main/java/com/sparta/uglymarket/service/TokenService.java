package com.sparta.uglymarket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.uglymarket.dto.TokenResponse;
import com.sparta.uglymarket.entity.RefreshToken;
import com.sparta.uglymarket.entity.TokenType;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.repository.AdminRepository;
import com.sparta.uglymarket.repository.RefreshTokenRepository;
import com.sparta.uglymarket.entity.AdminEntity;
import com.sparta.uglymarket.util.ITokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final ITokenUtil tokenUtil;
    private final AdminRepository adminRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    // 새로운 리프레시 토큰을 저장합니다.
    public void saveToken(AdminEntity admin, String refreshToken) {
        revokeAllUserTokens(admin); // 무효화 먼저 실행
        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .token(refreshToken)
                .tokenType(TokenType.REFRESH)
                .expired(false)
                .revoked(false)
                .phoneNumber(admin.getPhoneNumber())
                .build();
        refreshTokenRepository.save(refreshTokenEntity);
    }

    // 주어진 사용자의 모든 유효한 리프레시 토큰을 무효화합니다.
    public void revokeAllUserTokens(AdminEntity adminEntity) {
        List<RefreshToken> validTokens = refreshTokenRepository.findAllValidTokenByPhoneNumber(adminEntity.getPhoneNumber());
        if (!validTokens.isEmpty()) {
            validTokens.forEach(t -> {
                t.expire();
                t.revoke();
            });
            refreshTokenRepository.saveAll(validTokens);
        }
    }

    // 리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급합니다.
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String phoneNumber;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CustomException(ErrorMsg.MISSING_AUTHORIZATION_HEADER);
        }

        refreshToken = authHeader.substring(7);
        phoneNumber = tokenUtil.getPhoneNumberFromToken(refreshToken);

        if (phoneNumber != null) {
            AdminEntity admin = this.adminRepository.findByPhoneNumber(phoneNumber).orElse(null);

            if (admin != null && tokenUtil.validateToken(refreshToken)) {
                String accessToken = tokenUtil.generateAccessToken(admin.getPhoneNumber());
                TokenResponse authResponse = new TokenResponse(accessToken, refreshToken);

                response.setHeader("Authorization", "Bearer " + accessToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            } else {
                throw new CustomException(ErrorMsg.UNAUTHORIZED_MEMBER);
            }
        } else {
            throw new CustomException(ErrorMsg.UNAUTHORIZED_MEMBER);
        }
    }
}
