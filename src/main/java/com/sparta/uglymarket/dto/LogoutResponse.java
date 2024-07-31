package com.sparta.uglymarket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogoutResponse {
    private String message;

    public LogoutResponse(String message) {
        this.message = message;
    }
}
