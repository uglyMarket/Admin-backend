package com.sparta.uglymarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductsGetResponse {
    private Long id;
    private String title;
    private String content;
    private Long price;
    private Long stock;
    private String imageUrl;
    private String category;
}
