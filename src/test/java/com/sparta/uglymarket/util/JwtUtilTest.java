package com.sparta.uglymarket.util;

import com.sparta.uglymarket.entity.AdminEntity;
import com.sparta.uglymarket.entity.RefreshToken;
import com.sparta.uglymarket.repository.AdminRepository;
import com.sparta.uglymarket.repository.RefreshTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUtilTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtUtil.init();
    }

    @Test
    void testGenerateAccessToken() {
        String phoneNumber = "01012345678";
        String token = jwtUtil.generateAccessToken(phoneNumber);

        assertNotNull(token);
        assertEquals(phoneNumber, jwtUtil.getPhoneNumberFromToken(token));
    }

    @Test
    void testGenerateRefreshToken() {
        String phoneNumber = "01012345678";
        String token = jwtUtil.generateRefreshToken(phoneNumber);

        assertNotNull(token);
        assertEquals(phoneNumber, jwtUtil.getPhoneNumberFromToken(token));
    }
}
