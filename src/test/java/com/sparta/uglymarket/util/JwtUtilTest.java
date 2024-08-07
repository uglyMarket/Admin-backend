package com.sparta.uglymarket.util;

import com.sparta.uglymarket.entity.RefreshToken;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUtilTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private JwtUtil jwtUtil;

    private Key key;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        jwtUtil.init();

        // Reflection을 사용하여 private key 필드에 접근
        Field keyField = JwtUtil.class.getDeclaredField("key");
        keyField.setAccessible(true);
        key = (Key) keyField.get(jwtUtil);
    }

    @Test
    void testGenerateAccessToken() {
        // given
        String phoneNumber = "01012345678";

        // when
        String token = jwtUtil.generateAccessToken(phoneNumber);

        // then
        assertNotNull(token);
        String extractedPhoneNumber = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        assertEquals(phoneNumber, extractedPhoneNumber);
    }

    @Test
    void testGenerateRefreshToken() {
        // given
        String phoneNumber = "01012345678";

        // when
        String token = jwtUtil.generateRefreshToken(phoneNumber);

        // then
        assertNotNull(token);
        String extractedPhoneNumber = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        assertEquals(phoneNumber, extractedPhoneNumber);
    }

    @Test
    void testGetPhoneNumberFromToken() {
        // given
        String phoneNumber = "01012345678";
        String token = jwtUtil.generateAccessToken(phoneNumber);

        // when
        String extractedPhoneNumber = jwtUtil.getPhoneNumberFromToken(token);

        // then
        assertEquals(phoneNumber, extractedPhoneNumber);
    }

    @Test
    void testGetPhoneNumberFromExpiredToken() {
        // given
        String phoneNumber = "01012345678";
        String token = Jwts.builder()
                .setSubject(phoneNumber)
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000))
                .setExpiration(new Date(System.currentTimeMillis() - 500))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> {
            jwtUtil.getPhoneNumberFromToken(token);
        });
        assertEquals(ErrorMsg.INVALID_TOKEN.getDetails(), exception.getMessage());
    }

    @Test
    void testValidateToken() {
        // given
        String phoneNumber = "01012345678";
        String token = jwtUtil.generateAccessToken(phoneNumber);

        // when
        boolean isValid = jwtUtil.validateToken(token);

        // then
        assertTrue(isValid);
    }

    @Test
    void testValidateInvalidToken() {
        // given
        String token = "invalidToken";

        // when
        boolean isValid = jwtUtil.validateToken(token);

        // then
        assertFalse(isValid);
    }

    @Test
    void testRevokeToken() {
        // given
        String token = "refreshToken";
        RefreshToken refreshToken = new RefreshToken();
        when(refreshTokenRepository.findByToken(token)).thenReturn(java.util.Optional.of(refreshToken));

        // when
        jwtUtil.revokeToken(token);

        // then
        assertTrue(refreshToken.isExpired());
        assertTrue(refreshToken.isRevoked());
        verify(refreshTokenRepository).save(refreshToken);
    }
}
