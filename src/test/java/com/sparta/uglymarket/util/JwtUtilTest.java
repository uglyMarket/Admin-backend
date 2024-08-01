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

    @Test
    void testGetPhoneNumberFromToken() {
        String phoneNumber = "01012345678";
        String token = jwtUtil.generateAccessToken(phoneNumber);

        String extractedPhoneNumber = jwtUtil.getPhoneNumberFromToken(token);
        assertEquals(phoneNumber, extractedPhoneNumber);
    }

    @Test
    void testValidateToken() {
        String phoneNumber = "01012345678";
        String token = jwtUtil.generateAccessToken(phoneNumber);

        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void testRevokeToken() {
        String phoneNumber = "01012345678";
        String token = jwtUtil.generateRefreshToken(phoneNumber);

        RefreshToken refreshToken = mock(RefreshToken.class);
        when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.of(refreshToken));

        jwtUtil.revokeToken(token);

        verify(refreshToken, times(1)).expire();
        verify(refreshToken, times(1)).revoke();
        verify(refreshTokenRepository, times(1)).save(refreshToken);
    }

    @Test
    void testRefreshToken() throws Exception {
        String phoneNumber = "01012345678";
        String refreshToken = jwtUtil.generateRefreshToken(phoneNumber);
        AdminEntity adminEntity = mock(AdminEntity.class);
        when(adminEntity.getPhoneNumber()).thenReturn(phoneNumber);

        when(adminRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(adminEntity));
        when(refreshTokenRepository.findByToken(refreshToken)).thenReturn(Optional.of(mock(RefreshToken.class)));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + refreshToken);

        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtUtil.refreshToken(request, response);

        String newAccessToken = response.getHeader("Authorization").substring(7);
        assertNotNull(newAccessToken);
        assertEquals(phoneNumber, jwtUtil.getPhoneNumberFromToken(newAccessToken));
    }
}
