// AdminRepository 인터페이스로 추상화
package com.sparta.uglymarket.repository;

import com.sparta.uglymarket.entity.AdminEntity;

import java.util.Optional;

public interface AdminRepository {
    Optional<AdminEntity> findByPhoneNumber(String phoneNumber);
    AdminEntity save(AdminEntity adminEntity);
    Optional<AdminEntity> findById(Long id);
}
