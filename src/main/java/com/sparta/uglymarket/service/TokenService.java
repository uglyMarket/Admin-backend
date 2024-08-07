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


    // 액세스 토큰 생성
    public String generateAccessToken(String phoneNumber) {
        return tokenUtil.generateAccessToken(phoneNumber);
    }

    // 리프레시 토큰 생성
    public String generateRefreshToken(String phoneNumber) {
        return tokenUtil.generateRefreshToken(phoneNumber);
    }

    // 전화번호를 토큰에서 추출
    public String getPhoneNumberFromToken(String token) {
        return tokenUtil.getPhoneNumberFromToken(token);
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        return tokenUtil.validateToken(token);
    }

    // 토큰을 무효화
    public void revokeToken(String token) {
        tokenUtil.revokeToken(token);
    }

    // 새로운 리프레시 토큰을 저장합니다.
    public void saveToken(AdminEntity admin, String refreshToken) {
        revokeAllUserTokens(admin); // 무효화 먼저 실행
        RefreshToken refreshTokenEntity = createRefreshTokenEntity(admin, refreshToken);
        saveRefreshToken(refreshTokenEntity);
    }

    // 리프레시 토큰 엔티티 생성
    private RefreshToken createRefreshTokenEntity(AdminEntity admin, String refreshToken) {
        return RefreshToken.builder()
                .token(refreshToken)
                .tokenType(TokenType.REFRESH)
                .expired(false)
                .revoked(false)
                .phoneNumber(admin.getPhoneNumber())
                .build();
    }

    // 리프레시 토큰 엔티티 저장
    private void saveRefreshToken(RefreshToken refreshTokenEntity) {
        refreshTokenRepository.save(refreshTokenEntity);
    }

    // 주어진 사용자의 모든 유효한 리프레시 토큰을 무효화합니다.
    public void revokeAllUserTokens(AdminEntity adminEntity) {
        List<RefreshToken> validTokens = fetchValidTokens(adminEntity.getPhoneNumber());
        if (!validTokens.isEmpty()) {
            invalidateTokens(validTokens);
            saveAllTokens(validTokens);
        }
    }

    // 유효한 토큰 조회
    private List<RefreshToken> fetchValidTokens(String phoneNumber) {
        return refreshTokenRepository.findAllValidTokenByPhoneNumber(phoneNumber);
    }

    // 토큰 무효화
    private void invalidateTokens(List<RefreshToken> tokens) {
        tokens.forEach(t -> {
            t.expire();
            t.revoke();
        });
    }

    // 모든 토큰 저장
    private void saveAllTokens(List<RefreshToken> tokens) {
        refreshTokenRepository.saveAll(tokens);
    }

    // 리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급합니다.
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = extractAuthHeader(request);
        final String refreshToken = extractToken(authHeader);
        final String phoneNumber = tokenUtil.getPhoneNumberFromToken(refreshToken);

        validatePhoneNumber(phoneNumber);

        AdminEntity admin = validateAdmin(phoneNumber);
        validateToken(refreshToken);

        String accessToken = tokenUtil.generateAccessToken(admin.getPhoneNumber());
        setResponseWithTokens(response, accessToken, refreshToken);
    }

    // 전화번호 유효성 검사
    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            throw new CustomException(ErrorMsg.UNAUTHORIZED_MEMBER);
        }
    }

    // 관리자 유효성 검사
    private AdminEntity validateAdmin(String phoneNumber) {
        AdminEntity admin = findAdminByPhoneNumber(phoneNumber);
        if (admin == null) {
            throw new CustomException(ErrorMsg.UNAUTHORIZED_MEMBER);
        }
        return admin;
    }

    // Authorization 헤더 추출
    private String extractAuthHeader(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CustomException(ErrorMsg.MISSING_AUTHORIZATION_HEADER);
        }
        return authHeader;
    }

    // 토큰 추출
    private String extractToken(String authHeader) {
        return authHeader.substring(7);
    }

    // 전화번호로 관리자 조회
    private AdminEntity findAdminByPhoneNumber(String phoneNumber) {
        return adminRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }

    // 응답에 새로운 액세스 토큰과 리프레시 토큰 설정
    private void setResponseWithTokens(HttpServletResponse response, String accessToken, String refreshToken) throws IOException {
        TokenResponse authResponse = new TokenResponse(accessToken, refreshToken);
        response.setHeader("Authorization", "Bearer " + accessToken);
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
    }
}
