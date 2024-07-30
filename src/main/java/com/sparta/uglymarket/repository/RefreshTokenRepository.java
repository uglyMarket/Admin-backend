package com.sparta.uglymarket.repository;

import com.sparta.uglymarket.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    @Query("SELECT rt FROM RefreshToken rt WHERE rt.phoneNumber = :phoneNumber AND (rt.expired = false OR rt.revoked = false)")
    List<RefreshToken> findAllValidTokenByPhoneNumber(String phoneNumber);
    List<RefreshToken> findByPhoneNumber(String phoneNumber);
}
