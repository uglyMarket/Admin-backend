package com.sparta.uglymarket.service;

import com.sparta.uglymarket.entity.AdminEntity;
import com.sparta.uglymarket.entity.RefreshToken;
import com.sparta.uglymarket.entity.TokenType;
import com.sparta.uglymarket.repository.RefreshTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TokenServiceTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private TokenService tokenService;

    private AdminEntity adminEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminEntity = mock(AdminEntity.class);
        when(adminEntity.getPhoneNumber()).thenReturn("010-1234-5678");
    }

    @Test
    void testSaveToken() {
        String refreshToken = "sample_refresh_token";

        when(refreshTokenRepository.findAllValidTokenByPhoneNumber(anyString())).thenReturn(Collections.emptyList());

        tokenService.saveToken(adminEntity, refreshToken);

        verify(refreshTokenRepository, times(1)).findAllValidTokenByPhoneNumber("010-1234-5678");
        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
    }

    @Test
    void testRevokeAllUserTokens() {
        RefreshToken validToken = RefreshToken.builder()
                .token("valid_token")
                .tokenType(TokenType.REFRESH)
                .expired(false)
                .revoked(false)
                .phoneNumber("010-1234-5678")
                .build();

        when(refreshTokenRepository.findAllValidTokenByPhoneNumber(anyString())).thenReturn(List.of(validToken));

        tokenService.revokeAllUserTokens(adminEntity);

        verify(refreshTokenRepository, times(1)).findAllValidTokenByPhoneNumber("010-1234-5678");
        verify(refreshTokenRepository, times(1)).saveAll(anyList());
        assert validToken.isExpired();
        assert validToken.isRevoked();
    }
}
