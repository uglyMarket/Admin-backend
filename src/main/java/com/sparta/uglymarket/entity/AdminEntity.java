package com.sparta.uglymarket.entity;

import com.sparta.uglymarket.dto.AdminRegisterRequest;
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

    public AdminEntity(AdminRegisterRequest adminRegisterRequest) {
        this.phoneNumber = adminRegisterRequest.getPhoneNumber();
        this.password = adminRegisterRequest.getPassword();
        this.role = Role.ROLE_ADMIN;
        this.farmName = adminRegisterRequest.getFarmName();
        this.introMessage = adminRegisterRequest.getIntroMessage();
        this.profileImageUrl = adminRegisterRequest.getProfileImageUrl();
        this.leaderName = adminRegisterRequest.getLeaderName();
        this.businessId = adminRegisterRequest.getBusinessId();
        this.openingDate = adminRegisterRequest.getOpeningDate();
        this.minOrderAmount = adminRegisterRequest.getMinOrderAmount();
    }

    // 엔티티 수정 메서드
    public void update(AdminRegisterRequest adminRegisterRequest) {
        this.phoneNumber = adminRegisterRequest.getPhoneNumber();
        this.password = adminRegisterRequest.getPassword();
        this.farmName = adminRegisterRequest.getFarmName();
        this.introMessage = adminRegisterRequest.getIntroMessage();
        this.profileImageUrl = adminRegisterRequest.getProfileImageUrl();
        this.leaderName = adminRegisterRequest.getLeaderName();
        this.businessId = adminRegisterRequest.getBusinessId();
        this.openingDate = adminRegisterRequest.getOpeningDate();
        this.minOrderAmount = adminRegisterRequest.getMinOrderAmount();
    }
//    public Role getRole() {
//        return role;
//    }

    public void setRole(Role role) {
        this.role = role;
    }

    // 비밀번호 설정 메서드
    public void setPassword(String password) {
        this.password = password;
    }
}
