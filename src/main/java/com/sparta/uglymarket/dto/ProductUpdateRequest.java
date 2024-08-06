package com.sparta.uglymarket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductUpdateRequest {
    private String title; // 상품 제목
    private String content; // 상품 내용
    private Long price; // 상품 가격
    private Long stock; // 재고량
    private String imageUrl; // 상품 이미지 URL
    private String category; // 상품 카테고리
}
