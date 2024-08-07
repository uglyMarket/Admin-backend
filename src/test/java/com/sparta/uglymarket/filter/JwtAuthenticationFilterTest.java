//package com.sparta.uglymarket.filter;
//
//import com.sparta.uglymarket.exception.ErrorMsg;
//import com.sparta.uglymarket.service.TokenService;
//import com.sparta.uglymarket.util.JwtUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpServletResponse;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class JwtAuthenticationFilterTest {
//
//    @InjectMocks
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Mock
//    private JwtUtil jwtUtil;
//
//    @Mock
//    private TokenService tokenService;
//
//    @Mock
//    private FilterChain filterChain;
//
//    private MockHttpServletRequest request;
//    private MockHttpServletResponse response;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        request = new MockHttpServletRequest();
//        response = new MockHttpServletResponse();
//    }
//
//    @Test
//    void testDoFilterInternal_LoginAndRegisterPath() throws ServletException, IOException {
//        request.setRequestURI("/api/admin/login");
//
//        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
//
//        verify(filterChain, times(1)).doFilter(request, response);
//    }
//
//    @Test
//    void testDoFilterInternal_MissingAuthorizationHeader() throws ServletException, IOException {
//        request.setRequestURI("/api/test");
//
//        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
//
//        assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
//        assertEquals("{\"message\": \"Authorization 헤더가 없거나 형식이 올바르지 않습니다.\"}", response.getContentAsString());
//    }
//
//    @Test
//    void testDoFilterInternal_InvalidToken() throws ServletException, IOException {
//        request.setRequestURI("/api/test");
//        request.addHeader("Authorization", "Bearer invalidToken");
//
//        when(jwtUtil.getPhoneNumberFromToken("invalidToken")).thenReturn(null);
//
//        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
//
//        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
//        assertEquals("{\"message\": \"유효하지 않은 토큰입니다.\"}", response.getContentAsString());
//    }
//
//    @Test
//    void testDoFilterInternal_ValidToken() throws ServletException, IOException {
//        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
//        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
//
//        when(mockRequest.getRequestURI()).thenReturn("/api/test");
//        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer validToken");
//
//
//        when(jwtUtil.getPhoneNumberFromToken("validToken")).thenReturn("email@example.com");
//        when(jwtUtil.validateToken("validToken")).thenReturn(true);
//
//        jwtAuthenticationFilter.doFilterInternal(mockRequest, mockResponse, filterChain);
//
//        verify(mockRequest, times(1)).setAttribute("email", "email@example.com");
//        verify(filterChain, times(1)).doFilter(mockRequest, mockResponse);
//    }
//
//
//    @Test
//    void testDoFilterInternal_ExpiredAccessTokenAndValidRefreshToken() throws ServletException, IOException {
//        request.setRequestURI("/api/test");
//        request.addHeader("Authorization", "Bearer expiredAccessToken");
//        request.addHeader("Refresh-Token", "Bearer validRefreshToken");
//
//        when(jwtUtil.getPhoneNumberFromToken("expiredAccessToken")).thenReturn("email@example.com");
//        when(jwtUtil.validateToken("expiredAccessToken")).thenReturn(false);
//        when(jwtUtil.validateToken("validRefreshToken")).thenReturn(true);
//
//        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
//
//        verify(jwtUtil, times(1)).refreshToken(request, response);
//    }
//
//    @Test
//    void testDoFilterInternal_ExpiredAccessTokenAndExpiredRefreshToken() throws ServletException, IOException {
//        request.setRequestURI("/api/test");
//        request.addHeader("Authorization", "Bearer expiredAccessToken");
//        request.addHeader("Refresh-Token", "Bearer expiredRefreshToken");
//
//        when(jwtUtil.getPhoneNumberFromToken("expiredAccessToken")).thenReturn("email@example.com");
//        when(jwtUtil.validateToken("expiredAccessToken")).thenReturn(false);
//        when(jwtUtil.validateToken("expiredRefreshToken")).thenReturn(false);
//
//        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
//
//        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
//        assertEquals("{\"message\": \"유효하지 않은 토큰입니다.\"}", response.getContentAsString());
//    }
//}
