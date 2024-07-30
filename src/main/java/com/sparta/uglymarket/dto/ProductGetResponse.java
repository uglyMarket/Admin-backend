package com.sparta.uglymarket.dto;

import com.sparta.uglymarket.entity.ProductEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductGetResponse {
    private String message;
    private ProductEntity product;

    public ProductGetResponse(String message, ProductEntity product) {
        this.message = message;
        this.product = product;
    }
}
