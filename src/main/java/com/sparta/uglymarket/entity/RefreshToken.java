package com.sparta.uglymarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tokens", schema = "public")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_generator")
    @SequenceGenerator(name = "token_generator", sequenceName = "tokens_seq", allocationSize = 1)
    private Long id;

    private String phoneNumber;

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private boolean expired;

    private boolean revoked;

    public void expire() {
        this.expired = true;
    }

    public void revoke() {
        this.revoked = true;
    }

    // 토큰 값 반환 메서드
    public String getToken() {
        return this.token;
    }

}
