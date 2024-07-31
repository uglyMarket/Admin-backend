package com.sparta.uglymarket.entity;

import com.sparta.uglymarket.dto.ProductCreateRequest;
import com.sparta.uglymarket.dto.ProductUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 상품 ID

    @Column(nullable = false)
    private String title; // 상품 제목

    @Column(nullable = false)
    private String content; // 상품 내용

    @Column(nullable = false)
    private Long price; // 상품 가격

    @Column(nullable = false)
    private Long stock; // 재고량

    @Column(nullable = false)
    private String imageUrl; // 상품 이미지 URL

    @Column(nullable = false)
    private String category; // 상품 카테고리

    public ProductEntity(ProductCreateRequest productCreateRequest) {
        this.title = productCreateRequest.getTitle();
        this.content = productCreateRequest.getContent();
        this.price = productCreateRequest.getPrice();
        this.stock = productCreateRequest.getStock();
        this.imageUrl = productCreateRequest.getImageUrl();
        this.category = productCreateRequest.getCategory();
    }

    public void updateFromRequest(ProductUpdateRequest productUpdateRequest) {
        this.title = productUpdateRequest.getTitle();
        this.content = productUpdateRequest.getContent();
        this.price = productUpdateRequest.getPrice();
        this.stock = productUpdateRequest.getStock();
        this.imageUrl = productUpdateRequest.getImageUrl();
        this.category = productUpdateRequest.getCategory();
    }
}
