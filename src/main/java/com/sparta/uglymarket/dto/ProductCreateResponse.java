package com.sparta.uglymarket.dto;

import com.sparta.uglymarket.entity.ProductEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateResponse {
    private String message;
    private ProductEntity product;

    public ProductCreateResponse(String message, ProductEntity updatedProduct) {
        this.message = message;
        this.product = updatedProduct;
    }
}
