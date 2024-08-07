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
}
