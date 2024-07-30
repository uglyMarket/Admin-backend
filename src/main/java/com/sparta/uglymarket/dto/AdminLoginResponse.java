package com.sparta.uglymarket.dto;

import lombok.Getter;

@Getter
public class AdminLoginResponse {
    private final String message;

    public AdminLoginResponse(String message) {
        this.message = message;
    }
}
