package com.sparta.uglymarket.controller;

import com.sparta.uglymarket.domain.AdminDomain;
import com.sparta.uglymarket.dto.AdminLoginRequest;
import com.sparta.uglymarket.dto.AdminRegisterRequest;
import com.sparta.uglymarket.dto.AdminResponseDto;
import com.sparta.uglymarket.mapper.AdminMapper;
import com.sparta.uglymarket.service.AdminService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final AdminMapper adminMapper;

    public AdminController(AdminService adminService, AdminMapper adminMapper) {
        this.adminService = adminService;
        this.adminMapper = adminMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<AdminResponseDto> register(@RequestBody AdminRegisterRequest dto) {
        // 컨트롤러에서 DTO -> Domain 변환
        AdminDomain adminDomain = adminMapper.dtoToDomain(dto);
        // 비즈니스 로직은 서비스에 위임
        AdminDomain registeredAdmin = adminService.register(adminDomain);
        // Domain을 DTO로 변환하여 응답
        AdminResponseDto responseDto = adminMapper.domainToResponseDto(registeredAdmin);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AdminResponseDto> login(@RequestBody AdminLoginRequest adminLoginRequest) {
        // 컨트롤러에서 DTO -> Domain 변환
        AdminDomain adminDomain = adminMapper.dtoToDomain(adminLoginRequest);

        // 서비스 호출, 로그인 로직 및 JWT 발급 처리
        AdminResponseDto responseDto = adminService.login(adminDomain);

        // 검증 로직은 서비스에서 처리됐으므로 바로 응답
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + responseDto.getAccessToken())
                .header("Refresh-Token", "Bearer " + responseDto.getRefreshToken())
                .body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDto> getAdmin(@PathVariable Long id) {
        Optional<AdminDomain> adminOptional = adminService.findAdminById(id);

        if (adminOptional.isPresent()) {
            AdminDomain adminDomain = adminOptional.get();
            AdminResponseDto responseDto = adminMapper.domainToResponseDto(adminDomain);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminResponseDto> updateAdmin(
            @PathVariable Long id,
            @RequestBody AdminRegisterRequest dto) {
        // 컨트롤러에서 DTO -> Domain 변환
        AdminDomain adminDomain = adminMapper.dtoToDomain(dto);
        // 비즈니스 로직은 서비스에 위임
        AdminDomain updatedAdmin = adminService.updateAdmin(id, adminDomain);
        // Domain을 DTO로 변환하여 응답
        AdminResponseDto responseDto = adminMapper.domainToResponseDto(updatedAdmin);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
