package com.sparta.uglymarket.dto;

import com.sparta.uglymarket.entity.ProductEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductsGetResponse {
    private Long id; // 상품 ID
    private String title; // 상품 제목
    private String content; // 상품 내용
    private Long price; // 상품 가격
    private Long stock; // 재고량
    private String imageUrl; // 상품 이미지 URL
    private String category; // 상품 카테고리

    public ProductsGetResponse(ProductEntity product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.content = product.getContent();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.imageUrl = product.getImageUrl();
        this.category = product.getCategory();
    }
}
