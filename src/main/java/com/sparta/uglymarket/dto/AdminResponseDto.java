package com.sparta.uglymarket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminResponseDto {
    private String message;
    private String role; // Optional 필드
    private String accessToken;  // 토큰 필드 추가
    private String refreshToken; // 토큰 필드 추가
    private String farmName;
    private String introMessage;
    private String profileImageUrl;
    private String leaderName;
    private String businessId;
    private String openingDate;
    private Long minOrderAmount;
    private String phoneNumber;

    // 기본 생성자 (message만 필요할 때)
    public AdminResponseDto(String message) {
        this.message = message;
        this.role = null;
    }

    // 로그인 성공 시 사용되는 생성자
    public AdminResponseDto(String message, String role) {
        this.message = message;
        this.role = role;
    }

    // 모든 데이터를 포함하는 생성자 (회원 정보 반환 시)
    public AdminResponseDto(String message, String role, String farmName, String introMessage, String profileImageUrl,
                            String leaderName, String businessId, String openingDate, Long minOrderAmount, String phoneNumber) {
        this.message = message;
        this.role = role;
        this.farmName = farmName;
        this.introMessage = introMessage;
        this.profileImageUrl = profileImageUrl;
        this.leaderName = leaderName;
        this.businessId = businessId;
        this.openingDate = openingDate;
        this.minOrderAmount = minOrderAmount;
        this.phoneNumber = phoneNumber;
    }

    // 토큰 값을 설정하는 메서드 추가
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
