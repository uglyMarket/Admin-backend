package com.sparta.uglymarket.service;

import com.sparta.uglymarket.dto.*;
import com.sparta.uglymarket.entity.AdminEntity;
import com.sparta.uglymarket.entity.RefreshToken;
import com.sparta.uglymarket.entity.Role;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.repository.AdminRepository;
import com.sparta.uglymarket.repository.RefreshTokenRepository;
import com.sparta.uglymarket.util.JwtUtil;
import com.sparta.uglymarket.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordUtil passwordUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register() {
        // given
        AdminRegisterRequest request = mock(AdminRegisterRequest.class);
        when(request.getPhoneNumber()).thenReturn("010-1234-5678");
        when(request.getPassword()).thenReturn("password");
        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());
        when(passwordUtil.encodePassword(anyString())).thenReturn("encodedPassword");

        // when
        AdminRegisterResponse response = adminService.register(request);

        // then
        assertEquals("회원가입 성공!", response.getMessage());
        assertEquals(Role.ROLE_ADMIN.name(), response.getRole());
        verify(adminRepository, times(1)).save(any(AdminEntity.class));
    }

    @Test
    void register_throwsException_whenPhoneNumberExists() {
        // given
        AdminRegisterRequest request = mock(AdminRegisterRequest.class);
        when(request.getPhoneNumber()).thenReturn("010-1234-5678");
        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.of(new AdminEntity()));

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> adminService.register(request));
        assertEquals(ErrorMsg.DUPLICATE_PHONE_NUMBER.getHttpStatus(), exception.getHttpStatus());
    }
}
