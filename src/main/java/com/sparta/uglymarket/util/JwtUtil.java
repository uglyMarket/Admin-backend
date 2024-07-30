package com.sparta.uglymarket.util;

import com.sparta.uglymarket.dto.TokenResponse;
import com.sparta.uglymarket.entity.RefreshToken;
import com.sparta.uglymarket.repository.RefreshTokenRepository;
import com.sparta.uglymarket.entity.AdminEntity;
import com.sparta.uglymarket.repository.AdminRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    // JWT 서명을 위한 키
    private Key key;
    private final AdminRepository adminRepository;
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
    public String generateAccessToken(String phoneNumber) {
        return Jwts.builder()
                .setSubject(phoneNumber)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 전화번호를 사용해 리프레시 토큰을 생성
    public String generateRefreshToken(String phoneNumber) {
        return Jwts.builder()
                .setSubject(phoneNumber)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration * 1000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 토큰에서 전화번호 추출
    public String getPhoneNumberFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 개별 토큰을 무효화하는 메서드
    public void revokeToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElse(null);
        if (refreshToken != null) {
            refreshToken.expire();
            refreshToken.revoke();
            refreshTokenRepository.save(refreshToken);
        }
    }

    // 리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급합니다.
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Authorization 헤더에서 리프레시 토큰 추출
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String phoneNumber;

        // Authorization 헤더가 존재하지 않거나 Bearer 토큰이 아니면 401 Unauthorized 응답
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // Bearer 토큰에서 실제 리프레시 토큰 값 추출
        refreshToken = authHeader.substring(7);
        phoneNumber = getPhoneNumberFromToken(refreshToken);

        // 전화번호가 존재하고 리프레시 토큰이 유효한 경우
        if (phoneNumber != null) {
            AdminEntity admin = this.adminRepository.findByPhoneNumber(phoneNumber).orElse(null);

            // 사용자가 존재하고 리프레시 토큰이 유효한 경우 새로운 액세스 토큰 발급
            if (admin != null && validateToken(refreshToken)) {
                String accessToken = generateAccessToken(admin.getPhoneNumber());
                TokenResponse authResponse = new TokenResponse(accessToken, refreshToken);

                // 응답 헤더에 새로운 액세스 토큰 추가
                response.setHeader("Authorization", "Bearer " + accessToken);

                // 응답 본문에 액세스 토큰과 리프레시 토큰을 JSON 형식으로 작성
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            } else {
                // 사용자가 없거나 리프레시 토큰이 유효하지 않은 경우 401 Unauthorized 응답
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            // 전화번호가 존재하지 않는 경우 401 Unauthorized 응답
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
