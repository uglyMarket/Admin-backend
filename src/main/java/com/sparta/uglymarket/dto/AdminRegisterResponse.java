package com.sparta.uglymarket.dto;

import lombok.Getter;

@Getter
public class AdminRegisterResponse {
    private final String message;
    private final String role;

    public AdminRegisterResponse(String message, String role) {
        this.message = message;
        this.role = role;
    }
}
