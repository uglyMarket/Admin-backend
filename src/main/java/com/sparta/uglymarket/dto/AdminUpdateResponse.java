package com.sparta.uglymarket.dto;

import lombok.Getter;

@Getter
public class AdminUpdateResponse {
    private final String message;
    private final String role;

    public AdminUpdateResponse(String message, String role) {
        this.message = message;
        this.role = role;
    }
}
