//package com.sparta.uglymarket.service;
//
//import com.sparta.uglymarket.dto.*;
//import com.sparta.uglymarket.entity.AdminEntity;
//import com.sparta.uglymarket.entity.RefreshToken;
//import com.sparta.uglymarket.entity.Role;
//import com.sparta.uglymarket.exception.CustomException;
//import com.sparta.uglymarket.exception.ErrorMsg;
//import com.sparta.uglymarket.repository.AdminRepository;
//import com.sparta.uglymarket.repository.RefreshTokenRepository;
//import com.sparta.uglymarket.util.JwtUtil;
//import com.sparta.uglymarket.util.PasswordUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import java.util.Collections;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//class AdminServiceTest {
//
//    @InjectMocks
//    private AdminService adminService;
//
//    @Mock
//    private AdminRepository adminRepository;
//
//    @Mock
//    private JwtUtil jwtUtil;
//
//    @Mock
//    private RefreshTokenRepository refreshTokenRepository;
//
//    @Mock
//    private PasswordUtil passwordUtil;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void register() {
//        // given
//        AdminRegisterRequest request = mock(AdminRegisterRequest.class);
//        when(request.getPhoneNumber()).thenReturn("010-1234-5678");
//        when(request.getPassword()).thenReturn("password");
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());
//        when(passwordUtil.encodePassword(anyString())).thenReturn("encodedPassword");
//
//        // when
//        AdminRegisterResponse response = adminService.register(request);
//
//        // then
//        assertEquals("회원가입 성공!", response.getMessage());
//        assertEquals(Role.ROLE_ADMIN.name(), response.getRole());
//        verify(adminRepository, times(1)).save(any(AdminEntity.class));
//    }
//
//    @Test
//    void register_throwsException_whenPhoneNumberExists() {
//        // given
//        AdminRegisterRequest request = mock(AdminRegisterRequest.class);
//        when(request.getPhoneNumber()).thenReturn("010-1234-5678");
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(new AdminEntity()));
//
//        // when & then
//        CustomException exception = assertThrows(CustomException.class, () -> adminService.register(request));
//        assertEquals(ErrorMsg.DUPLICATE_PHONE_NUMBER.getHttpStatus(), exception.getHttpStatus());
//    }
//
//    @Test
//    void login() {
//        // given
//        AdminLoginRequest request = mock(AdminLoginRequest.class);
//        when(request.getPhoneNumber()).thenReturn("010-1234-5678");
//        when(request.getPassword()).thenReturn("password");
//
//        AdminEntity admin = mock(AdminEntity.class);
//        when(admin.getPhoneNumber()).thenReturn("010-1234-5678");
//        when(admin.getPassword()).thenReturn("encodedPassword");
//
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(admin));
//        when(passwordUtil.matches(anyString(), anyString())).thenReturn(true);
//        when(jwtUtil.generateAccessToken(anyString())).thenReturn("accessToken");
//        when(jwtUtil.generateRefreshToken(anyString())).thenReturn("refreshToken");
//
//        // when
//        ResponseEntity<AdminLoginResponse> response = adminService.login(request);
//
//        // then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("로그인 성공!", response.getBody().getMessage());
//        assertTrue(response.getHeaders().containsKey("Authorization"));
//        assertTrue(response.getHeaders().containsKey("Refresh-Token"));
//    }
//
//    @Test
//    void login_throwsException_whenPhoneNumberNotFound() {
//        // given
//        AdminLoginRequest request = mock(AdminLoginRequest.class);
//        when(request.getPhoneNumber()).thenReturn("010-1234-5678");
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());
//
//        // when & then
//        CustomException exception = assertThrows(CustomException.class, () -> adminService.login(request));
//        assertEquals(ErrorMsg.PHONE_NUMBER_NOT_FOUND.getHttpStatus(), exception.getHttpStatus());
//    }
//
//    @Test
//    void login_throwsException_whenPasswordInvalid() {
//        // given
//        AdminLoginRequest request = mock(AdminLoginRequest.class);
//        when(request.getPhoneNumber()).thenReturn("010-1234-5678");
//        when(request.getPassword()).thenReturn("password");
//
//        AdminEntity admin = mock(AdminEntity.class);
//        when(admin.getPhoneNumber()).thenReturn("010-1234-5678");
//        when(admin.getPassword()).thenReturn("encodedPassword");
//
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(admin));
//        when(passwordUtil.matches(anyString(), anyString())).thenReturn(false);
//
//        // when & then
//        CustomException exception = assertThrows(CustomException.class, () -> adminService.login(request));
//        assertEquals(ErrorMsg.INVALID_PASSWORD.getHttpStatus(), exception.getHttpStatus());
//    }
//
//    @Test
//    void updateAdmin() {
//        // given
//        Long id = 1L;
//        AdminUpdateRequest request = mock(AdminUpdateRequest.class);
//        when(request.getPhoneNumber()).thenReturn("010-1234-5678");
//
//        AdminEntity admin = mock(AdminEntity.class);
//        when(admin.getPhoneNumber()).thenReturn("010-1234-5678");
//        when(admin.getRole()).thenReturn(Role.ROLE_ADMIN);
//
//        when(adminRepository.findById(eq(id))).thenReturn(Optional.of(admin));
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());
//
//        // when
//        AdminUpdateResponse response = adminService.updateAdmin(id, request);
//
//        // then
//        assertEquals("회원 정보 수정 성공!", response.getMessage());
//        assertEquals(Role.ROLE_ADMIN.name(), response.getRole());
//        verify(adminRepository, times(1)).save(admin);
//    }
//
//    @Test
//    void updateAdmin_throwsException_whenAdminNotFound() {
//        // given
//        Long id = 1L;
//        AdminUpdateRequest request = mock(AdminUpdateRequest.class);
//        when(adminRepository.findById(eq(id))).thenReturn(Optional.empty());
//
//        // when & then
//        CustomException exception = assertThrows(CustomException.class, () -> adminService.updateAdmin(id, request));
//        assertEquals(ErrorMsg.ADMIN_NOT_FOUND.getHttpStatus(), exception.getHttpStatus());
//    }
//
//    @Test
//    void updateAdmin_throwsException_whenPhoneNumberExists() {
//        // given
//        Long id = 1L;
//        AdminUpdateRequest request = mock(AdminUpdateRequest.class);
//        when(request.getPhoneNumber()).thenReturn("010-1234-5678");
//
//        AdminEntity admin = mock(AdminEntity.class);
//        when(admin.getPhoneNumber()).thenReturn("010-8765-4321");
//
//        when(adminRepository.findById(eq(id))).thenReturn(Optional.of(admin));
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(new AdminEntity()));
//
//        // when & then
//        CustomException exception = assertThrows(CustomException.class, () -> adminService.updateAdmin(id, request));
//        assertEquals(ErrorMsg.DUPLICATE_PHONE_NUMBER.getHttpStatus(), exception.getHttpStatus());
//    }
//
//    @Test
//    void logout() {
//        // given
//        String token = "someToken";
//        when(jwtUtil.getPhoneNumberFromToken(anyString())).thenReturn("010-1234-5678");
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(new AdminEntity()));
//        when(refreshTokenRepository.findByPhoneNumber(anyString())).thenReturn(Collections.singletonList(new RefreshToken()));
//
//        // when
//        LogoutResponse response = adminService.logout(token);
//
//        // then
//        assertEquals("로그아웃 성공!", response.getMessage());
//        verify(jwtUtil, times(1)).revokeToken(eq(token));
//        verify(jwtUtil, times(1)).revokeToken(anyString());
//    }
//
//    @Test
//    void logout_throwsException_whenPhoneNumberNotFound() {
//        // given
//        String token = "someToken";
//        when(jwtUtil.getPhoneNumberFromToken(anyString())).thenReturn("010-1234-5678");
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());
//
//        // when & then
//        CustomException exception = assertThrows(CustomException.class, () -> adminService.logout(token));
//        assertEquals(ErrorMsg.PHONE_NUMBER_NOT_FOUND.getHttpStatus(), exception.getHttpStatus());
//    }
//}
