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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordUtil passwordUtil;

    // 회원가입
    public AdminRegisterResponse register(AdminRegisterRequest adminRegisterRequest) {
        if (adminRepository.findByPhoneNumber(adminRegisterRequest.getPhoneNumber()).isPresent()) {
            throw new CustomException(ErrorMsg.DUPLICATE_PHONE_NUMBER);
        }

        AdminEntity adminEntity = new AdminEntity(adminRegisterRequest);
        String encodedPassword = passwordUtil.encodePassword(adminRegisterRequest.getPassword());
        adminEntity.setPassword(encodedPassword);

        adminEntity.setRole(Role.ROLE_ADMIN);
        adminRepository.save(adminEntity);
        return new AdminRegisterResponse("회원가입 성공!", adminEntity.getRole().name());
    }

    // 로그인
    public ResponseEntity<AdminLoginResponse> login(AdminLoginRequest requestDto) {
        AdminEntity admin = adminRepository.findByPhoneNumber(requestDto.getPhoneNumber())
                .orElseThrow(() -> new CustomException(ErrorMsg.PHONE_NUMBER_NOT_FOUND));

        if (!passwordUtil.matches(requestDto.getPassword(), admin.getPassword())) {
            throw new CustomException(ErrorMsg.INVALID_PASSWORD);
        }

        String token = jwtUtil.generateAccessToken(admin.getPhoneNumber());
        String refreshToken = jwtUtil.generateRefreshToken(admin.getPhoneNumber());

        AdminLoginResponse responseDto = new AdminLoginResponse("로그인 성공!");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Refresh-Token", "Bearer " + refreshToken);

        return new ResponseEntity<>(responseDto, headers, HttpStatus.OK);
    }

    // 회원 정보 수정
    public AdminUpdateResponse updateAdmin(Long id, AdminUpdateRequest adminRequest) {
        AdminEntity admin = adminRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.ADMIN_NOT_FOUND));

        if (!admin.getPhoneNumber().equals(adminRequest.getPhoneNumber()) &&
                adminRepository.findByPhoneNumber(adminRequest.getPhoneNumber()).isPresent()) {
            throw new CustomException(ErrorMsg.DUPLICATE_PHONE_NUMBER);
        }

        admin.update(adminRequest);
        adminRepository.save(admin);
        return new AdminUpdateResponse("회원 정보 수정 성공!", admin.getRole().name());
    }

    // 로그아웃
    public LogoutResponse logout(String token) {
        String phoneNumber = jwtUtil.getPhoneNumberFromToken(token);
        adminRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(ErrorMsg.PHONE_NUMBER_NOT_FOUND));

        jwtUtil.revokeToken(token);
        List<RefreshToken> refreshTokens = refreshTokenRepository.findByPhoneNumber(phoneNumber);
        refreshTokens.forEach(refreshToken -> {
            jwtUtil.revokeToken(refreshToken.getToken());
        });

        return new LogoutResponse("로그아웃 성공!");
    }
}
