package com.sparta.uglymarket.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AdminRequest {
    private String farmName; // 농장 이름
    private String introMessage; // 소개 메시지
    private String profileImageUrl; // 프로필 사진 URL
    private String phoneNumber; // 전화번호
    private String leaderName; // 대표자 이름
    private String businessId; // 사업자 등록번호
    private String openingDate; // 개업일
    private Long minOrderAmount; // 최소 주문금액
    private String password; // 비밀번호
}
