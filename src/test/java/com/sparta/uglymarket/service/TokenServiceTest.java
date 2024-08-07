package com.sparta.uglymarket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.uglymarket.dto.TokenResponse;
import com.sparta.uglymarket.entity.AdminEntity;
import com.sparta.uglymarket.entity.RefreshToken;
import com.sparta.uglymarket.entity.Role;
import com.sparta.uglymarket.entity.TokenType;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.repository.AdminRepository;
import com.sparta.uglymarket.repository.RefreshTokenRepository;
import com.sparta.uglymarket.util.ITokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TokenServiceTest {

    @Mock
    private ITokenUtil tokenUtil;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private TokenService tokenService;

    private AdminEntity adminEntity;
    private RefreshToken refreshToken;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        adminEntity = new AdminEntity(
                1L, "01012345678", "encodedPassword",
                Role.ROLE_ADMIN, "farmName",
                "introMessage", "profileImageUrl",
                "leaderName", "businessId",
                "openingDate", 10000L);

        refreshToken = RefreshToken.builder()
                .token("refreshToken")
                .tokenType(TokenType.REFRESH)
                .expired(false)
                .revoked(false)
                .phoneNumber("01012345678")
                .build();

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    void testGenerateAccessToken() {
        // given
        when(tokenUtil.generateAccessToken(anyString())).thenReturn("accessToken");

        // when
        String accessToken = tokenService.generateAccessToken("01012345678");

        // then
        assertEquals("accessToken", accessToken);
    }

    @Test
    void testGenerateRefreshToken() {
        // given
        when(tokenUtil.generateRefreshToken(anyString())).thenReturn("refreshToken");

        // when
        String refreshToken = tokenService.generateRefreshToken("01012345678");

        // then
        assertEquals("refreshToken", refreshToken);
    }

    @Test
    void testGetPhoneNumberFromToken() {
        // given
        when(tokenUtil.getPhoneNumberFromToken(anyString())).thenReturn("01012345678");

        // when
        String phoneNumber = tokenService.getPhoneNumberFromToken("token");

        // then
        assertEquals("01012345678", phoneNumber);
    }
}
