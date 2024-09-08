package com.sparta.uglymarket.repository;

import com.sparta.uglymarket.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// AdminRepository의 실제 구현체
@Repository
public interface AdminRepositoryImpl extends JpaRepository<AdminEntity, Long>, AdminRepository {
    @Override
    Optional<AdminEntity> findByPhoneNumber(String phoneNumber);
}
