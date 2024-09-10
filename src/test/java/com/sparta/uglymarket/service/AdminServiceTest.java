//package com.sparta.uglymarket.service;
//
//import com.sparta.uglymarket.dto.*;
//import com.sparta.uglymarket.entity.AdminEntity;
//import com.sparta.uglymarket.entity.RefreshToken;
//import com.sparta.uglymarket.entity.Role;
//import com.sparta.uglymarket.exception.CustomException;
//import com.sparta.uglymarket.exception.ErrorMsg;
//import com.sparta.uglymarket.factory.AdminFactory;
//import com.sparta.uglymarket.repository.AdminRepository;
//import com.sparta.uglymarket.repository.RefreshTokenRepository;
//import com.sparta.uglymarket.util.PasswordUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//class AdminServiceTest {
//
//    @Mock
//    private AdminRepository adminRepository;
//
//    @Mock
//    private TokenService tokenService;
//
//    @Mock
//    private RefreshTokenRepository refreshTokenRepository;
//
//    @Mock
//    private PasswordUtil passwordUtil;
//
//    @Mock
//    private AdminFactory adminFactory;
//
//    @InjectMocks
//    private AdminService adminService;
//
//    private AdminRegisterRequest adminRegisterRequest;
//    private AdminLoginRequest adminLoginRequest;
//    private AdminUpdateRequest adminUpdateRequest;
//    private AdminEntity adminEntity;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        adminRegisterRequest = mock(AdminRegisterRequest.class);
//        adminLoginRequest = mock(AdminLoginRequest.class);
//        adminUpdateRequest = mock(AdminUpdateRequest.class);
//
//        adminEntity = new AdminEntity(
//                1L, "01012345678", "encodedPassword",
//                Role.ROLE_ADMIN, "farmName",
//                "introMessage", "profileImageUrl",
//                "leaderName", "businessId",
//                "openingDate", 10000L);
//
//        when(adminFactory.createAdmin(any())).thenReturn(adminEntity);
//        when(passwordUtil.encodePassword(anyString())).thenReturn("encodedPassword");
//    }
//
//    @Test
//    void testRegister() {
//        // given
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());
//        when(adminFactory.createAdmin(adminRegisterRequest)).thenReturn(adminEntity);
//        when(passwordUtil.encodePassword(anyString())).thenReturn("encodedPassword");
//
//        // when
//        AdminRegisterResponse response = adminService.register(adminRegisterRequest);
//
//        // then
//        verify(adminRepository).save(adminEntity);
//        assertEquals("회원가입 성공!", response.getMessage());
//    }
//
//
//    @Test
//    void testRegister_ThrowsException() {
//        // given
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(adminEntity));
//        when(adminRegisterRequest.getPhoneNumber()).thenReturn("01012345678");
//
//        // when & then
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            adminService.register(adminRegisterRequest);
//        });
//
//        assertEquals(ErrorMsg.DUPLICATE_PHONE_NUMBER.getDetails(), exception.getMessage());
//    }
//
//    @Test
//    void testAuthenticateUser_ThrowsException() {
//        // given
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());
//
//        // when & then
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            adminService.login(adminLoginRequest);
//        });
//
//        assertEquals(ErrorMsg.PHONE_NUMBER_NOT_FOUND.getDetails(), exception.getMessage());
//    }
//
//    @Test
//    void testUpdateAdmin() {
//        // given
//        when(adminRepository.findById(anyLong())).thenReturn(Optional.of(adminEntity));
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(adminEntity));
//
//        // when
//        AdminUpdateResponse response = adminService.updateAdmin(1L, adminUpdateRequest);
//
//        // then
//        verify(adminRepository).save(adminEntity);
//        assertEquals("회원 정보 수정 성공!", response.getMessage());
//    }
//
//    @Test
//    void testUpdateAdmin_ThrowsException() {
//        // given
//        when(adminRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        // when & then
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            adminService.updateAdmin(1L, adminUpdateRequest);
//        });
//
//        assertEquals(ErrorMsg.ADMIN_NOT_FOUND.getDetails(), exception.getMessage());
//    }
//
//    @Test
//    void testLogout() {
//        // given
//        String token = "testToken";
//        when(tokenService.getPhoneNumberFromToken(token)).thenReturn("01012345678");
//        when(adminRepository.findByPhoneNumber("01012345678")).thenReturn(Optional.of(adminEntity));
//        when(refreshTokenRepository.findByPhoneNumber("01012345678")).thenReturn(List.of(new RefreshToken()));
//
//        // when
//        LogoutResponse response = adminService.logout(token);
//
//        // then
//        assertEquals("로그아웃 성공!", response.getMessage());
//        verify(tokenService).revokeToken(token);
//    }
//
//    @Test
//    void testLogout_ThrowsException() {
//        // given
//        String token = "testToken";
//        when(tokenService.getPhoneNumberFromToken(token)).thenReturn("01012345678");
//        when(adminRepository.findByPhoneNumber("01012345678")).thenReturn(Optional.empty());
//
//        // when & then
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            adminService.logout(token);
//        });
//
//        assertEquals(ErrorMsg.PHONE_NUMBER_NOT_FOUND.getDetails(), exception.getMessage());
//    }
//
//    @Test
//    void testLogin() {
//        // given
//        when(adminLoginRequest.getPhoneNumber()).thenReturn("01012345678");
//        when(adminLoginRequest.getPassword()).thenReturn("password");
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(adminEntity));
//        when(passwordUtil.matches(anyString(), anyString())).thenReturn(true);
//        when(tokenService.generateAccessToken(anyString())).thenReturn("accessToken");
//        when(tokenService.generateRefreshToken(anyString())).thenReturn("refreshToken");
//
//        // when
//        ResponseEntity<AdminLoginResponse> response = adminService.login(adminLoginRequest);
//
//        // then
//        assertEquals("로그인 성공!", response.getBody().getMessage());
//        assertEquals("Bearer accessToken", response.getHeaders().getFirst("Authorization"));
//        assertEquals("Bearer refreshToken", response.getHeaders().getFirst("Refresh-Token"));
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void testAuthenticateUser() {
//        // given
//        when(adminLoginRequest.getPhoneNumber()).thenReturn("01012345678");
//        when(adminLoginRequest.getPassword()).thenReturn("password");
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(adminEntity));
//        when(passwordUtil.matches(anyString(), anyString())).thenReturn(true);
//        when(tokenService.generateAccessToken(anyString())).thenReturn("accessToken");
//        when(tokenService.generateRefreshToken(anyString())).thenReturn("refreshToken");
//
//        // when
//        ResponseEntity<AdminLoginResponse> response = adminService.login(adminLoginRequest);
//
//        // then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals("로그인 성공!", response.getBody().getMessage());
//        assertEquals("Bearer accessToken", response.getHeaders().getFirst("Authorization"));
//        assertEquals("Bearer refreshToken", response.getHeaders().getFirst("Refresh-Token"));
//    }
//
//    @Test
//    void testCreateHeaders() {
//        // given
//        String token = "accessToken";
//        String refreshToken = "refreshToken";
//        when(adminLoginRequest.getPhoneNumber()).thenReturn("01012345678");
//        when(adminLoginRequest.getPassword()).thenReturn("password");
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(adminEntity));
//        when(passwordUtil.matches(anyString(), anyString())).thenReturn(true);
//        when(tokenService.generateAccessToken(anyString())).thenReturn(token);
//        when(tokenService.generateRefreshToken(anyString())).thenReturn(refreshToken);
//
//        // when
//        ResponseEntity<AdminLoginResponse> response = adminService.login(adminLoginRequest);
//
//        // then
//        HttpHeaders headers = response.getHeaders();
//        assertEquals("Bearer " + token, headers.getFirst("Authorization"));
//        assertEquals("Bearer " + refreshToken, headers.getFirst("Refresh-Token"));
//    }
//
//    @Test
//    void testCheckForDuplicatePhoneNumberIfChanged() {
//        // given
//        when(adminRepository.findById(anyLong())).thenReturn(Optional.of(adminEntity));
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(adminEntity));
//        when(adminUpdateRequest.getPhoneNumber()).thenReturn("newPhoneNumber");
//
//        // when & then
//        CustomException exception = assertThrows(CustomException.class, () -> {
//            adminService.updateAdmin(1L, adminUpdateRequest);
//        });
//        assertEquals(ErrorMsg.DUPLICATE_PHONE_NUMBER.getDetails(), exception.getMessage());
//    }
//}
