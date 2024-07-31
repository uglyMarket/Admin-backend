package com.sparta.uglymarket.service;

import com.sparta.uglymarket.entity.RefreshToken;
import com.sparta.uglymarket.entity.TokenType;
import com.sparta.uglymarket.repository.RefreshTokenRepository;
import com.sparta.uglymarket.entity.AdminEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

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
}
