package com.sparta.uglymarket.mapper;

import com.sparta.uglymarket.domain.AdminDomain;
import com.sparta.uglymarket.dto.AdminLoginRequest;
import com.sparta.uglymarket.dto.AdminRegisterRequest;
import com.sparta.uglymarket.dto.AdminResponseDto;
import com.sparta.uglymarket.entity.AdminEntity;
import com.sparta.uglymarket.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    // AdminLoginRequest -> Domain 변환
    public AdminDomain dtoToDomain(AdminLoginRequest dto) {
        return new AdminDomain(
                null,                           // id
                null,                           // name (로그인에는 필요하지 않음)
                dto.getPassword(),              // password
                null,                           // nickName (로그인에는 필요하지 않음)
                dto.getPhoneNumber(),           // phoneNumber
                Role.ROLE_ADMIN                 // 기본 role 설정 (예시)
        );
    }

    // AdminRegisterRequest -> Domain 변환
    public AdminDomain dtoToDomain(AdminRegisterRequest dto) {
        return new AdminDomain(
                null,                           // id
                dto.getLeaderName(),            // name
                dto.getPassword(),              // password
                dto.getLeaderName(),            // nickName
                dto.getPhoneNumber(),           // phoneNumber
                Role.ROLE_ADMIN                 // 기본 role 설정 (예시)
        );
    }

    // Domain -> ResponseDto 변환
    public AdminResponseDto domainToResponseDto(AdminDomain domain) {
        return new AdminResponseDto(
                "요청 성공",  // 공통 메시지
                domain.getRole().toString(),
                domain.getName(),
                domain.getNickName(),
                domain.getPhoneNumber(),
                domain.getName(),
                domain.getId().toString(),
                domain.getId().toString(),
                5000L,  // 임시 최소 주문금액, 실제 구현에서는 필드에 맞게 업데이트 필요
                domain.getPhoneNumber()
        );
    }



    // Entity -> Domain 변환
    public AdminDomain toDomain(AdminEntity entity) {
        return new AdminDomain(
                entity.getId(),
                entity.getName(),
                entity.getPassword(),
                entity.getNickName(),
                entity.getPhoneNumber(),
                entity.getRole()
        );
    }

    // Domain -> Entity 변환
    public AdminEntity toEntity(AdminDomain domain) {
        return new AdminEntity(
                domain.getName(),
                domain.getPassword(),
                domain.getNickName(),
                domain.getPhoneNumber(),
                domain.getRole()
        );
    }

    // AdminDomain -> AdminResponseDto 변환 (role 필요 없는 경우)
    public AdminResponseDto domainToResponseDto(String message) {
        return new AdminResponseDto(message);
    }

    // AdminDomain -> AdminResponseDto 변환 (role 포함)
    public AdminResponseDto domainToResponseDto(String message, String role) {
        return new AdminResponseDto(message, role);
    }
}
