package com.sparta.uglymarket.service;

import com.sparta.uglymarket.domain.AdminDomain;
import com.sparta.uglymarket.dto.AdminResponseDto;
import com.sparta.uglymarket.entity.AdminEntity;
import com.sparta.uglymarket.mapper.AdminMapper;
import com.sparta.uglymarket.repository.AdminRepository;
import com.sparta.uglymarket.util.JwtUtil;
import com.sparta.uglymarket.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordUtil passwordUtil;
    private final JwtUtil jwtUtil;

    // 관리자 등록
    public AdminDomain register(AdminDomain adminDomain) {
        // 비밀번호 암호화 등 핵심 비즈니스 로직
        adminDomain.encryptPassword(passwordUtil);
        // 도메인 정보로 엔티티 변환 후 저장
        AdminEntity adminEntity = adminMapper.toEntity(adminDomain);
        AdminEntity savedAdmin = adminRepository.save(adminEntity);
        return adminMapper.toDomain(savedAdmin);
    }

    // 관리자 조회
    public Optional<AdminDomain> findAdminById(Long id) {
        return adminRepository.findById(id)
                .map(adminMapper::toDomain);  // Entity를 Domain으로 변환
    }


    // 로그인 로직 처리 및 JWT 발급
    public AdminResponseDto login(AdminDomain adminDomain) {
        // 전화번호로 관리자 조회
        AdminEntity adminEntity = adminRepository.findByPhoneNumber(adminDomain.getPhoneNumber())
                .orElseThrow(() -> new IllegalArgumentException("해당 관리자가 존재하지 않습니다."));

        // 비밀번호 검증 (핵심 비즈니스 로직)
        if (!passwordUtil.matches(adminDomain.getPassword(), adminEntity.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }

        // JWT 토큰 발급
        String accessToken = jwtUtil.generateAccessToken(adminEntity.getPhoneNumber());
        String refreshToken = jwtUtil.generateRefreshToken(adminEntity.getPhoneNumber());

        // Domain -> Response DTO 변환 및 토큰 추가
        AdminResponseDto responseDto = adminMapper.domainToResponseDto(adminMapper.toDomain(adminEntity));
        responseDto.setAccessToken(accessToken);
        responseDto.setRefreshToken(refreshToken);

        return responseDto;
    }

    // 관리자 정보 수정
    public AdminDomain updateAdmin(Long id, AdminDomain adminDomain) {
        AdminEntity existingAdminEntity = adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 관리자가 존재하지 않습니다."));

        // 기존 엔티티에서 도메인으로 변환하여 업데이트 정보 반영
        AdminDomain existingAdminDomain = adminMapper.toDomain(existingAdminEntity);

        // 정보 업데이트 (핵심 비즈니스 로직은 서비스 레이어에서 처리)
        existingAdminDomain.updateAdminInfo(
                adminDomain.getPassword(),
                adminDomain.getNickName(),
                adminDomain.getPhoneNumber(),
                passwordUtil
        );

        // 엔티티로 변환 후 저장
        AdminEntity updatedAdminEntity = adminRepository.save(adminMapper.toEntity(existingAdminDomain));
        return adminMapper.toDomain(updatedAdminEntity);
    }
}
