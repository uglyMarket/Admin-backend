package com.sparta.uglymarket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDeleteResponse {
    private String message;

    public ProductDeleteResponse(String message) {
        this.message = message;
    }
}