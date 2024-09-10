package com.sparta.uglymarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDeleteResponse {
    private String message;  // 삭제 성공 메시지
}
