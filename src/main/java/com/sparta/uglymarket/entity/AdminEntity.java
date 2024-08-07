package com.sparta.uglymarket.entity;

import com.sparta.uglymarket.dto.AdminRegisterRequest;
import com.sparta.uglymarket.dto.AdminUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admin")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false, unique = true)
    private String farmName;

    @Column(nullable = false)
    private String introMessage;

    @Column(nullable = false)
    private String profileImageUrl;

    @Column(nullable = false)
    private String leaderName;

    @Column(nullable = false)
    private String businessId;

    @Column(nullable = false)
    private String openingDate;

    @Column(nullable = false)
    private Long minOrderAmount;


    // 엔티티 수정 메서드
    public void update(AdminUpdateRequest adminUpdateRequest) {
        this.phoneNumber = adminUpdateRequest.getPhoneNumber();
        this.password = adminUpdateRequest.getPassword();
        this.farmName = adminUpdateRequest.getFarmName();
        this.introMessage = adminUpdateRequest.getIntroMessage();
        this.profileImageUrl = adminUpdateRequest.getProfileImageUrl();
        this.leaderName = adminUpdateRequest.getLeaderName();
        this.businessId = adminUpdateRequest.getBusinessId();
        this.openingDate = adminUpdateRequest.getOpeningDate();
        this.minOrderAmount = adminUpdateRequest.getMinOrderAmount();
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // 비밀번호 설정 메서드
    public void setPassword(String password) {
        this.password = password;
    }
}
