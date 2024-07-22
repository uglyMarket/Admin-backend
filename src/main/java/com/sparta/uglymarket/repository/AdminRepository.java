package com.sparta.uglymarket.repository;

import com.sparta.uglymarket.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    Optional<AdminEntity> findByPhoneNumber(String phoneNumber);
    Optional<AdminEntity> findByFarmName(String farmName);
}
