package com.sparta.uglymarket.filter;

import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.service.TokenService;
import com.sparta.uglymarket.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = mock(FilterChain.class);
    }

    @Test
    void testDoFilterInternal_WithInvalidToken() throws ServletException, IOException {
        // given
        String token = "invalidToken";
        request.setRequestURI("/api/some/protected/endpoint");
        request.addHeader("Authorization", "Bearer " + token);
        when(jwtUtil.getPhoneNumberFromToken(token)).thenReturn(null);
        when(jwtUtil.validateToken(token)).thenReturn(false);

        // when
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // then
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals("UTF-8", response.getCharacterEncoding());
        assertTrue(response.getContentAsString().contains(ErrorMsg.INVALID_TOKEN.getDetails()));
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithMissingToken() throws ServletException, IOException {
        // given
        request.setRequestURI("/api/some/protected/endpoint");
        // Authorization 헤더를 추가하지 않음

        // when
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // then
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals("UTF-8", response.getCharacterEncoding());
        assertTrue(response.getContentAsString().contains(ErrorMsg.MISSING_AUTHORIZATION_HEADER.getDetails()));
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithValidToken() throws ServletException, IOException {
        // given
        String token = "validToken";
        String phoneNumber = "test@example.com";
        request.setRequestURI("/api/some/protected/endpoint");
        request.addHeader("Authorization", "Bearer " + token);
        when(jwtUtil.getPhoneNumberFromToken(token)).thenReturn(phoneNumber);
        when(jwtUtil.validateToken(token)).thenReturn(true);

        // when
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // then
        assertEquals(phoneNumber, request.getAttribute("email"));
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithInvalidRefreshToken() throws ServletException, IOException {
        // given
        String token = "invalidToken";
        String refreshToken = "invalidRefreshToken";
        request.setRequestURI("/api/some/protected/endpoint");
        request.addHeader("Authorization", "Bearer " + token);
        request.addHeader("Refresh-Token", "Bearer " + refreshToken);
        when(jwtUtil.getPhoneNumberFromToken(token)).thenReturn(null);
        when(jwtUtil.validateToken(token)).thenReturn(false);
        when(jwtUtil.validateToken(refreshToken)).thenReturn(false);

        // when
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // then
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals("UTF-8", response.getCharacterEncoding());
        assertTrue(response.getContentAsString().contains(ErrorMsg.INVALID_TOKEN.getDetails()));
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testHandleInvalidToken_WithValidRefreshToken() throws ServletException, IOException {
        // given
        String token = "invalidToken";
        String refreshToken = "validRefreshToken";
        request.setRequestURI("/api/some/protected/endpoint");
        request.addHeader("Authorization", "Bearer " + token);
        request.addHeader("Refresh-Token", "Bearer " + refreshToken);
        when(jwtUtil.getPhoneNumberFromToken(token)).thenReturn(null);
        when(jwtUtil.validateToken(token)).thenReturn(false);
        when(jwtUtil.validateToken(refreshToken)).thenReturn(true);

        // when
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(tokenService).refreshToken(request, response);
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithSkipPath() throws ServletException, IOException {
        // given
        request.setRequestURI("/api/admin/login");

        // when
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testShouldSkipFilter() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // given
        Method method = JwtAuthenticationFilter.class.getDeclaredMethod("shouldSkipFilter", String.class);
        method.setAccessible(true);

        // when
        boolean result1 = (boolean) method.invoke(jwtAuthenticationFilter, "/api/admin/login");
        boolean result2 = (boolean) method.invoke(jwtAuthenticationFilter, "/api/admin/register");
        boolean result3 = (boolean) method.invoke(jwtAuthenticationFilter, "/api/some/other/path");

        // then
        assertTrue(result1);
        assertTrue(result2);
        assertFalse(result3);
    }

    @Test
    void testExtractToken() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // given
        Method method = JwtAuthenticationFilter.class.getDeclaredMethod("extractToken", String.class);
        method.setAccessible(true);

        // when
        String result1 = (String) method.invoke(jwtAuthenticationFilter, "Bearer validToken");
        String result2 = (String) method.invoke(jwtAuthenticationFilter, (String) null);
        String result3 = (String) method.invoke(jwtAuthenticationFilter, "InvalidToken");

        // then
        assertEquals("validToken", result1);
        assertNull(result2);
        assertNull(result3);
    }

    @Test
    void testIsValidToken() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // given
        Method method = JwtAuthenticationFilter.class.getDeclaredMethod("isValidToken", String.class);
        method.setAccessible(true);
        when(jwtUtil.getPhoneNumberFromToken("validToken")).thenReturn("test@example.com");
        when(jwtUtil.validateToken("validToken")).thenReturn(true);
        when(jwtUtil.getPhoneNumberFromToken("invalidToken")).thenReturn(null);
        when(jwtUtil.validateToken("invalidToken")).thenReturn(false);

        // when
        boolean result1 = (boolean) method.invoke(jwtAuthenticationFilter, "validToken");
        boolean result2 = (boolean) method.invoke(jwtAuthenticationFilter, "invalidToken");

        // then
        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    void testTryRefreshToken() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
        // given
        Method method = JwtAuthenticationFilter.class.getDeclaredMethod("tryRefreshToken", HttpServletRequest.class, HttpServletResponse.class);
        method.setAccessible(true);
        String refreshToken = "validRefreshToken";
        request.addHeader("Refresh-Token", "Bearer " + refreshToken);
        when(jwtUtil.validateToken(refreshToken)).thenReturn(true);

        // when
        method.invoke(jwtAuthenticationFilter, request, response);

        // then
        verify(tokenService).refreshToken(request, response);
    }

    @Test
    void testHandleInvalidToken() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ServletException, IOException {
        // given
        Method method = JwtAuthenticationFilter.class.getDeclaredMethod("handleInvalidToken", HttpServletRequest.class, HttpServletResponse.class, String.class);
        method.setAccessible(true);
        String invalidToken = "invalidToken";
        request.addHeader("Authorization", "Bearer " + invalidToken);
        when(jwtUtil.validateToken(invalidToken)).thenReturn(false);
        when(jwtUtil.getPhoneNumberFromToken(invalidToken)).thenReturn(null);

        // when
        method.invoke(jwtAuthenticationFilter, request, response, invalidToken);

        // then
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals("UTF-8", response.getCharacterEncoding());
        assertTrue(response.getContentAsString().contains(ErrorMsg.INVALID_TOKEN.getDetails()));
    }
}
