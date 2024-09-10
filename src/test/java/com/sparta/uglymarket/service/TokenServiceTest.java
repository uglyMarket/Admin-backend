//package com.sparta.uglymarket.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sparta.uglymarket.dto.TokenResponse;
//import com.sparta.uglymarket.entity.AdminEntity;
//import com.sparta.uglymarket.entity.RefreshToken;
//import com.sparta.uglymarket.entity.Role;
//import com.sparta.uglymarket.entity.TokenType;
//import com.sparta.uglymarket.exception.CustomException;
//import com.sparta.uglymarket.exception.ErrorMsg;
//import com.sparta.uglymarket.repository.AdminRepository;
//import com.sparta.uglymarket.repository.RefreshTokenRepository;
//import com.sparta.uglymarket.util.ITokenUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpHeaders;
//import org.springframework.mock.web.MockHttpServletResponse;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.*;
//
//class TokenServiceTest {
//
//    @Mock
//    private ITokenUtil tokenUtil;
//
//    @Mock
//    private AdminRepository adminRepository;
//
//    @Mock
//    private RefreshTokenRepository refreshTokenRepository;
//
//    @InjectMocks
//    private TokenService tokenService;
//
//    private AdminEntity adminEntity;
//    private RefreshToken refreshToken;
//    private HttpServletRequest request;
//    private HttpServletResponse response;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        adminEntity = new AdminEntity(
//                1L, "01012345678", "encodedPassword",
//                Role.ROLE_ADMIN, "farmName",
//                "introMessage", "profileImageUrl",
//                "leaderName", "businessId",
//                "openingDate", 10000L);
//
//        refreshToken = RefreshToken.builder()
//                .token("refreshToken")
//                .tokenType(TokenType.REFRESH)
//                .expired(false)
//                .revoked(false)
//                .phoneNumber("01012345678")
//                .build();
//
//        request = mock(HttpServletRequest.class);
//        response = mock(HttpServletResponse.class);
//    }
//
//    @Test
//    void testGenerateAccessToken() {
//        // given
//        when(tokenUtil.generateAccessToken(anyString())).thenReturn("accessToken");
//
//        // when
//        String accessToken = tokenService.generateAccessToken("01012345678");
//
//        // then
//        assertEquals("accessToken", accessToken);
//    }
//
//    @Test
//    void testGenerateRefreshToken() {
//        // given
//        when(tokenUtil.generateRefreshToken(anyString())).thenReturn("refreshToken");
//
//        // when
//        String refreshToken = tokenService.generateRefreshToken("01012345678");
//
//        // then
//        assertEquals("refreshToken", refreshToken);
//    }
//
//    @Test
//    void testGetPhoneNumberFromToken() {
//        // given
//        when(tokenUtil.getPhoneNumberFromToken(anyString())).thenReturn("01012345678");
//
//        // when
//        String phoneNumber = tokenService.getPhoneNumberFromToken("token");
//
//        // then
//        assertEquals("01012345678", phoneNumber);
//    }
//
//    @Test
//    void testValidateToken() {
//        // given
//        when(tokenUtil.validateToken(anyString())).thenReturn(true);
//
//        // when
//        boolean isValid = tokenService.validateToken("token");
//
//        // then
//        assertTrue(isValid);
//    }
//
//    @Test
//    void testRevokeToken() {
//        // given
//        doNothing().when(tokenUtil).revokeToken(anyString());
//
//        // when
//        tokenService.revokeToken("token");
//
//        // then
//        verify(tokenUtil).revokeToken("token");
//    }
//
//    @Test
//    void testSaveToken() {
//        // given
//        when(refreshTokenRepository.save(any(RefreshToken.class))).thenReturn(refreshToken);
//
//        // when
//        tokenService.saveToken(adminEntity, "refreshToken");
//
//        // then
//        verify(refreshTokenRepository).save(any(RefreshToken.class));
//    }
//
//    @Test
//    void testRevokeAllUserTokens() {
//        // given
//        when(refreshTokenRepository.findAllValidTokenByPhoneNumber(anyString())).thenReturn(List.of(refreshToken));
//        when(refreshTokenRepository.saveAll(anyList())).thenReturn(List.of(refreshToken));
//
//        // when
//        tokenService.revokeAllUserTokens(adminEntity);
//
//        // then
//        verify(refreshTokenRepository).saveAll(anyList());
//    }
//
//    @Test
//    void testRefreshToken() throws IOException {
//        // given
//        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer refreshToken");
//        when(tokenUtil.getPhoneNumberFromToken(anyString())).thenReturn("01012345678");
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(adminEntity));
//        when(tokenUtil.validateToken(anyString())).thenReturn(true);
//        when(tokenUtil.generateAccessToken(anyString())).thenReturn("newAccessToken");
//
//        MockHttpServletResponse mockResponse = new MockHttpServletResponse();
//        mockResponse.setCharacterEncoding("UTF-8"); // Ensure the character encoding is set
//
//        // when
//        tokenService.refreshToken(request, mockResponse);
//
//        // then
//        assertEquals("Bearer newAccessToken", mockResponse.getHeader("Authorization"));
//        String responseContent = mockResponse.getContentAsString();
//        assertTrue(responseContent.contains("newAccessToken"));
//        assertTrue(responseContent.contains("refreshToken"));
//    }
//
//    @Test
//    void testRefreshToken_MissingAuthorizationHeader() {
//        // given
//        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);
//
//        // when & then
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            tokenService.refreshToken(request, response);
//        });
//
//        assertEquals(ErrorMsg.MISSING_AUTHORIZATION_HEADER.getDetails(), exception.getMessage());
//    }
//
//    @Test
//    void testRefreshToken_UnauthorizedMember() {
//        // given
//        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer refreshToken");
//        when(tokenUtil.getPhoneNumberFromToken(anyString())).thenReturn(null);
//
//        // when & then
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            tokenService.refreshToken(request, response);
//        });
//
//        assertEquals(ErrorMsg.UNAUTHORIZED_MEMBER.getDetails(), exception.getMessage());
//    }
//
//    @Test
//    void testRefreshToken_InvalidAdmin() {
//        // given
//        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer refreshToken");
//        when(tokenUtil.getPhoneNumberFromToken(anyString())).thenReturn("01012345678");
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());
//
//        // when & then
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            tokenService.refreshToken(request, response);
//        });
//
//        assertEquals(ErrorMsg.UNAUTHORIZED_MEMBER.getDetails(), exception.getMessage());
//    }
//}
