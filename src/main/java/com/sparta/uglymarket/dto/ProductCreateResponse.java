package com.sparta.uglymarket.dto;

import com.sparta.uglymarket.domain.ProductDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductCreateResponse {
    private String message;  // 응답 메시지
    private ProductDomain product;  // 생성된 상품 정보
}
