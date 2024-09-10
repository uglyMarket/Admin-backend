package com.sparta.uglymarket.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "admins")
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // 기본 생성자
    public AdminEntity() {}

    public AdminEntity(String name, String password, String nickName, String phoneNumber, Role role) {
        this.name = name;
        this.password = password;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }
}
