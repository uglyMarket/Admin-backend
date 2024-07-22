package com.sparta.uglymarket.entity;

import com.sparta.uglymarket.dto.AdminRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "admin")
@NoArgsConstructor
@Getter
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId; // 관리자 ID

    @Column(nullable = false, unique = true, length = 15)
    private String farmName; // 농장 이름

    @Column(nullable = false, length = 500)
    private String introMessage; // 소개 메시지

    @Column(nullable = false)
    private String profileImageUrl; // 프로필 사진 URL

    @Column(nullable = false, unique = true, length = 13)
    private String phoneNumber; // 전화번호

    @Column(nullable = false, length = 15)
    private String leaderName; // 대표자 이름

    @Column(nullable = false, length = 12)
    private String businessId; // 사업자 등록번호

    @Column(nullable = false, length = 10)
    private String openingDate; // 개업일

    @Column(nullable = false)
    private Long minOrderAmount; // 최소 주문금액

    @Column(nullable = false)
    private String password; // 비밀번호


    public AdminEntity(AdminRequest adminRequest) {
        this.farmName = adminRequest.getFarmName();
        this.introMessage = adminRequest.getIntroMessage();
        this.profileImageUrl = adminRequest.getProfileImageUrl();
        this.phoneNumber = adminRequest.getPhoneNumber();
        this.leaderName = adminRequest.getLeaderName();
        this.businessId = adminRequest.getBusinessId();
        this.openingDate = adminRequest.getOpeningDate();
        this.minOrderAmount = adminRequest.getMinOrderAmount();
        this.password = adminRequest.getPassword();
    }

}
