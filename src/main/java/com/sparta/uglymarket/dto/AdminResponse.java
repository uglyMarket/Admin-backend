package com.sparta.uglymarket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminResponse {
    private String status; // 응답 상태 ("success" 또는 "error")
    private String message; // 응답 메시지

    public AdminResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }
}