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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
}
