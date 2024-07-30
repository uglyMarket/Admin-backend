package com.sparta.uglymarket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AdminRegisterRequest {
    @NotBlank(message = "농장 이름은 필수 항목입니다.")
    private String farmName; // 농장 이름

    @NotBlank(message = "소개 메시지는 필수 항목입니다.")
    private String introMessage; // 소개 메시지

    @NotBlank(message = "프로필 사진 URL은 필수 항목입니다.")
    private String profileImageUrl; // 프로필 사진 URL

    @NotBlank(message = "전화번호는 필수 항목입니다.")
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phoneNumber; // 전화번호

    @NotBlank(message = "대표자 이름은 필수 항목입니다.")
    private String leaderName; // 대표자 이름

    @NotBlank(message = "사업자 등록번호는 필수 항목입니다.")
    private String businessId; // 사업자 등록번호

    @NotBlank(message = "개업일은 필수 항목입니다.")
    private String openingDate; // 개업일

    @NotNull(message = "최소 주문금액은 필수 항목입니다.")
    private Long minOrderAmount; // 최소 주문금액

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password; // 비밀번호
}
