package com.sparta.uglymarket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AdminRegisterRequest extends AdminBaseRequestDto {
    @NotBlank(message = "농장 이름은 필수 항목입니다.")
    private String farmName;

    @NotBlank(message = "소개 메시지는 필수 항목입니다.")
    private String introMessage;

    @NotBlank(message = "프로필 사진 URL은 필수 항목입니다.")
    private String profileImageUrl;

    @NotBlank(message = "대표자 이름은 필수 항목입니다.")
    private String leaderName;

    @NotBlank(message = "사업자 등록번호는 필수 항목입니다.")
    private String businessId;

    @NotBlank(message = "개업일은 필수 항목입니다.")
    private String openingDate;

    @NotNull(message = "최소 주문금액은 필수 항목입니다.")
    private Long minOrderAmount;
}
