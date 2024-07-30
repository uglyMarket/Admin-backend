package com.sparta.uglymarket.dto;

import lombok.Getter;

@Getter
public class AdminLoginRequest {
    private String phoneNumber;
    private String password;
}
