package com.sparta.uglymarket.util;

import com.sparta.uglymarket.entity.RefreshToken;
import com.sparta.uglymarket.repository.RefreshTokenRepository;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil implements ITokenUtil {

    private Key key;
    private final RefreshTokenRepository refreshTokenRepository;

    // 객체 초기화 후 키 생성
    @PostConstruct
    public void init() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    // 액세스 토큰 만료 시간 (1시간)
    private final int accessTokenExpiration = 3600;
    // 리프레시 토큰 만료 시간 (2주)
    private final int refreshTokenExpiration = 1209600;

    // 전화번호를 사용해 액세스 토큰을 생성
    @Override
    public String generateAccessToken(String phoneNumber) {
        return Jwts.builder()
                .setSubject(phoneNumber)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 전화번호를 사용해 리프레시 토큰을 생성
    @Override
    public String generateRefreshToken(String phoneNumber) {
        return Jwts.builder()
                .setSubject(phoneNumber)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 토큰에서 전화번호 추출
    @Override
    public String getPhoneNumberFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorMsg.INVALID_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(ErrorMsg.INVALID_TOKEN);
        }
    }

    // 토큰 유효성 검증
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 토큰을 무효화
    @Override
    public void revokeToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElse(null);
        if (refreshToken != null) {
            refreshToken.expire();
            refreshToken.revoke();
            refreshTokenRepository.save(refreshToken);
        }
    }
}
