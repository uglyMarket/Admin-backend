package com.sparta.uglymarket.dto;

import com.sparta.uglymarket.domain.ProductDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductUpdateResponse {
    private String message;  // 응답 메시지
    private ProductDomain updatedProduct;  // 업데이트된 상품 정보
}
