package com.sparta.uglymarket.util;

import com.sparta.uglymarket.entity.AdminEntity;
import com.sparta.uglymarket.entity.RefreshToken;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.repository.AdminRepository;
import com.sparta.uglymarket.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Date;
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

    @Test
    void testRefreshTokenWithInvalidHeader() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "InvalidToken");

        MockHttpServletResponse response = new MockHttpServletResponse();

        assertThrows(CustomException.class, () -> jwtUtil.refreshToken(request, response));
    }

    @Test
    void testRefreshTokenWithNullHeader() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();

        MockHttpServletResponse response = new MockHttpServletResponse();

        assertThrows(CustomException.class, () -> jwtUtil.refreshToken(request, response));
    }

    @Test
    void testRefreshTokenWithInvalidToken() throws Exception {
        // 유효하지만 잘못된 서명을 가진 토큰 생성
        String phoneNumber = "01012345678";
        String validToken = jwtUtil.generateRefreshToken(phoneNumber);

        // 유효한 토큰의 마지막 부분을 잘못된 문자열로 변경하여 잘못된 서명을 가진 토큰으로 만듭니다.
        String invalidToken = validToken.substring(0, validToken.lastIndexOf('.') + 1) + "invalidSignature";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + invalidToken);

        MockHttpServletResponse response = new MockHttpServletResponse();

        assertThrows(CustomException.class, () -> jwtUtil.refreshToken(request, response));
    }



    @Test
    void testRefreshTokenWithExpiredToken() throws Exception {
        String phoneNumber = "01012345678";
        String expiredToken = Jwts.builder()
                .setSubject(phoneNumber)
                .setIssuedAt(new Date(System.currentTimeMillis() - 3600000)) // 1 hour ago
                .setExpiration(new Date(System.currentTimeMillis() - 1800000)) // 30 minutes ago
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS512), SignatureAlgorithm.HS512)
                .compact();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + expiredToken);

        MockHttpServletResponse response = new MockHttpServletResponse();

        assertThrows(CustomException.class, () -> jwtUtil.refreshToken(request, response));
    }
}
