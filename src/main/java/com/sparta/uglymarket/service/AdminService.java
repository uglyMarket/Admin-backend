package com.sparta.uglymarket.service;

import com.sparta.uglymarket.dto.AdminRequest;
import com.sparta.uglymarket.dto.AdminResponse;
import com.sparta.uglymarket.entity.AdminEntity;
import com.sparta.uglymarket.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository; // AdminRepository 객체를 주입받습니다.
    private final PasswordEncoder passwordEncoder; // PasswordEncoder 객체를 주입받습니다.

    public AdminResponse register(AdminRequest adminRequest) {
        // 이미 해당 전화번호로 등록된 관리자가 있는지 확인합니다.                 isPresent() 존재하는지 안하는지 확인메서드
        if (adminRepository.findByPhoneNumber(adminRequest.getPhoneNumber()).isPresent()) {
            return new AdminResponse("error", "전화번호가 이미 존재합니다."); // 전화번호 중복 확인
        }
        // 이미 해당 농장 이름으로 등록된 관리자가 있는지 확인합니다.
        if (adminRepository.findByFarmName(adminRequest.getFarmName()).isPresent()) {
            return new AdminResponse("error", "농장 이름이 이미 존재합니다."); // 농장 이름 중복 확인
        }
        // 비밀번호를 해싱하여 보안을 강화합니다.
        String hashedPassword = passwordEncoder.encode(adminRequest.getPassword()); // 비밀번호 해싱
        adminRequest.setPassword(hashedPassword); // 해싱된 비밀번호 설정
        // AdminRequest를 AdminEntity로 변환하여 데이터베이스에 저장합니다.
        AdminEntity adminEntity = new AdminEntity(adminRequest);
        adminRepository.save(adminEntity); // 관리자 정보 저장


        return new AdminResponse("success", "회원가입이 성공적으로 완료되었습니다."); // 성공 메시지 반환
    }

}
