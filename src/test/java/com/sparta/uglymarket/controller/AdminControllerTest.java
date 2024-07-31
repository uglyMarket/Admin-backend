package com.sparta.uglymarket.controller;

import com.sparta.uglymarket.dto.*;
import com.sparta.uglymarket.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register() {
        // given
        AdminRegisterRequest request = mock(AdminRegisterRequest.class);
        AdminRegisterResponse response = mock(AdminRegisterResponse.class);
        when(adminService.register(any(AdminRegisterRequest.class))).thenReturn(response);

        // when
        ResponseEntity<AdminRegisterResponse> result = adminController.register(request);

        // then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(adminService, times(1)).register(request);
    }

    @Test
    void login() {
        // given
        AdminLoginRequest request = mock(AdminLoginRequest.class);
        AdminLoginResponse loginResponse = mock(AdminLoginResponse.class);
        ResponseEntity<AdminLoginResponse> response = new ResponseEntity<>(loginResponse, HttpStatus.OK);
        when(adminService.login(any(AdminLoginRequest.class))).thenReturn(response);

        // when
        ResponseEntity<AdminLoginResponse> result = adminController.login(request);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response.getBody(), result.getBody());
        verify(adminService, times(1)).login(request);
    }

    @Test
    void updateAdmin() {
        // given
        Long id = 1L;
        AdminRegisterRequest request = mock(AdminRegisterRequest.class);
        AdminUpdateResponse response = mock(AdminUpdateResponse.class);
        when(adminService.updateAdmin(eq(id), any(AdminRegisterRequest.class))).thenReturn(response);

        // when
        ResponseEntity<AdminUpdateResponse> result = adminController.updateAdmin(id, request);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(adminService, times(1)).updateAdmin(eq(id), eq(request));
    }

    @Test
    void logout() {
        // given
        String token = "someToken";
        LogoutResponse response = mock(LogoutResponse.class);
        when(adminService.logout(eq(token))).thenReturn(response);

        // when
        ResponseEntity<LogoutResponse> result = adminController.logout(token);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(adminService, times(1)).logout(eq(token));
    }
}
