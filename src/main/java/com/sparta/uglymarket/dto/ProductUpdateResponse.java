package com.sparta.uglymarket.dto;

import com.sparta.uglymarket.entity.ProductEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductUpdateResponse {
    private String message;
    private ProductEntity product;

    public ProductUpdateResponse(String message, ProductEntity product) {
        this.message = message;
        this.product = product;
    }
}
