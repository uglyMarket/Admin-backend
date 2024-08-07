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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordUtil passwordUtil;

    @Autowired
    public AdminService(AdminRepository adminRepository, JwtUtil jwtUtil, TokenService tokenService, RefreshTokenRepository refreshTokenRepository, PasswordUtil passwordUtil) {
        this.adminRepository = adminRepository;
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordUtil = passwordUtil;
    }

    // 회원 가입
    public AdminRegisterResponse register(AdminRegisterRequest adminRegisterRequest) {
        checkForDuplicatePhoneNumber(adminRegisterRequest.getPhoneNumber());
        AdminEntity adminEntity = createAdminEntity(adminRegisterRequest);
        adminRepository.save(adminEntity);
        return new AdminRegisterResponse("회원가입 성공!", adminEntity.getRole().name());
    }

    // 관리자 로그인 메서드
    public ResponseEntity<AdminLoginResponse> login(AdminLoginRequest adminLoginRequest) {
        AdminEntity admin = authenticateUser(adminLoginRequest);
        String token = tokenService.generateAccessToken(admin.getPhoneNumber());
        String refreshToken = tokenService.generateRefreshToken(admin.getPhoneNumber());
        tokenService.saveToken(admin, refreshToken);
        HttpHeaders headers = createHeaders(token, refreshToken);

        AdminLoginResponse responseDto = new AdminLoginResponse("로그인 성공!");
        return new ResponseEntity<>(responseDto, headers, HttpStatus.OK);
    }


    // 회원 정보 수정
    public AdminUpdateResponse updateAdmin(Long id, AdminUpdateRequest adminUpdateRequest) {
        AdminEntity admin = findAdminById(id);
        checkForDuplicatePhoneNumberIfChanged(admin, adminUpdateRequest.getPhoneNumber());
        updateAdminEntity(admin, adminUpdateRequest);
        return new AdminUpdateResponse("회원 정보 수정 성공!", admin.getRole().name());
    }


    // 로그아웃
    public LogoutResponse logout(String token) {
        String phoneNumber = jwtUtil.getPhoneNumberFromToken(token);
        adminRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(ErrorMsg.PHONE_NUMBER_NOT_FOUND));

        // 액세스 토큰 무효화
        jwtUtil.revokeToken(token);
        List<RefreshToken> refreshTokens = refreshTokenRepository.findByPhoneNumber(phoneNumber);
        refreshTokens.forEach(refreshToken -> {
            jwtUtil.revokeToken(refreshToken.getToken());
        });

        return new LogoutResponse("로그아웃 성공!");
    }

    // 전화번호 중복 체크
    private void checkForDuplicatePhoneNumber(String phoneNumber) {
        if (adminRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new CustomException(ErrorMsg.DUPLICATE_PHONE_NUMBER);
        }
    }

    // AdminRegisterRequest를 AdminEntity로 변환하고 비밀번호를 암호화하여 엔티티 생성
    private AdminEntity createAdminEntity(AdminRegisterRequest adminRegisterRequest) {
        AdminEntity adminEntity = new AdminEntity(adminRegisterRequest);
        String encodedPassword = passwordUtil.encodePassword(adminRegisterRequest.getPassword());
        adminEntity.setPassword(encodedPassword);
        adminEntity.setRole(Role.ROLE_ADMIN);
        return adminEntity;
    }

    // 사용자 인증 메서드
    private AdminEntity authenticateUser(AdminLoginRequest adminLoginRequest) {
        AdminEntity admin = adminRepository.findByPhoneNumber(adminLoginRequest.getPhoneNumber())
                .orElseThrow(() -> new CustomException(ErrorMsg.PHONE_NUMBER_NOT_FOUND));
        if (!passwordUtil.matches(adminLoginRequest.getPassword(), admin.getPassword())) {
            throw new CustomException(ErrorMsg.INVALID_PASSWORD);
        }
        return admin;
    }

    // 헤더 생성 메서드
    private HttpHeaders createHeaders(String token, String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Refresh-Token", "Bearer " + refreshToken);
        return headers;
    }

    // ID로 관리자 엔티티를 찾고, 없으면 예외 발생
    private AdminEntity findAdminById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.ADMIN_NOT_FOUND));
    }

    // 전화번호가 변경된 경우 중복 체크
    private void checkForDuplicatePhoneNumberIfChanged(AdminEntity admin, String newPhoneNumber) {
        if (!admin.getPhoneNumber().equals(newPhoneNumber) &&
                adminRepository.findByPhoneNumber(newPhoneNumber).isPresent()) {
            throw new CustomException(ErrorMsg.DUPLICATE_PHONE_NUMBER);
        }
    }

    // 관리자 엔티티 업데이트
    private void updateAdminEntity(AdminEntity admin, AdminUpdateRequest adminUpdateRequest) {
        admin.update(adminUpdateRequest);
        adminRepository.save(admin);
    }
}
