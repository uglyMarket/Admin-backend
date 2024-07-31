package com.sparta.uglymarket.service;

import com.sparta.uglymarket.dto.AdminRegisterRequest;
import com.sparta.uglymarket.dto.AdminRegisterResponse;
import com.sparta.uglymarket.entity.AdminEntity;
import com.sparta.uglymarket.entity.Role;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.repository.AdminRepository;
import com.sparta.uglymarket.repository.RefreshTokenRepository;
import com.sparta.uglymarket.util.JwtUtil;
import com.sparta.uglymarket.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
