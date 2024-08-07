package com.sparta.uglymarket.util;

public interface ITokenUtil {
    String generateAccessToken(String phoneNumber);
    String generateRefreshToken(String phoneNumber);
    String getPhoneNumberFromToken(String token);
    boolean validateToken(String token);
    void revokeToken(String token);
}