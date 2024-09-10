package com.sparta.uglymarket.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateRequest {

    @NotBlank(message = "상품 제목은 필수 항목입니다.")
    private String title;

    @NotBlank(message = "상품 내용은 필수 항목입니다.")
    private String content;

    @NotNull(message = "가격은 필수 항목입니다.")
    @Min(value = 1, message = "가격은 1원 이상이어야 합니다.")
    private Long price;

    @NotNull(message = "재고는 필수 항목입니다.")
    @Min(value = 0, message = "재고는 0 이상이어야 합니다.")
    private Long stock;

    @NotBlank(message = "상품 이미지는 필수 항목입니다.")
    private String imageUrl;

    @NotBlank(message = "카테고리는 필수 항목입니다.")
    private String category;
}
