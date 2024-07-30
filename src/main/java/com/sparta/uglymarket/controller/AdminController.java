package com.sparta.uglymarket.controller;

import com.sparta.uglymarket.dto.*;
import com.sparta.uglymarket.service.AdminService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    // 관리자 회원 가입
    @PostMapping("/register")
    public ResponseEntity<AdminRegisterResponse> register(@Valid @RequestBody AdminRegisterRequest adminRequest) {
        AdminRegisterResponse adminRegisterResponse = adminService.register(adminRequest);
        return new ResponseEntity<>(adminRegisterResponse, HttpStatus.CREATED);
    }


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(@RequestBody AdminLoginRequest adminLoginRequest) {
        return adminService.login(adminLoginRequest);
    }

    // 회원 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<AdminUpdateResponse> updateAdmin(@PathVariable Long id, @Valid @RequestBody AdminRegisterRequest adminRequest) {
        AdminUpdateResponse adminUpdateResponse = adminService.updateAdmin(id, adminRequest);
        return new ResponseEntity<>(adminUpdateResponse, HttpStatus.OK);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(@RequestHeader("Authorization") String token) {
        LogoutResponse logoutResponse = adminService.logout(token);
        return new ResponseEntity<>(logoutResponse, HttpStatus.OK);
    }
}
