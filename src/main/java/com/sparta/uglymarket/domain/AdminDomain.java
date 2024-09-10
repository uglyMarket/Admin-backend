package com.sparta.uglymarket.domain;

import com.sparta.uglymarket.entity.Role;
import com.sparta.uglymarket.util.PasswordUtil;

public class AdminDomain {

    private Long id;
    private String name;
    private String password;
    private String nickName;
    private String phoneNumber;
    private Role role;

    // 생성자: 객체 생성 시 비즈니스 규칙 검증
    public AdminDomain(Long id, String name, String password, String nickName, String phoneNumber, Role role) {
        this.id = id;
        this.name = name;
        setPassword(password);  // 비밀번호 검증 포함
        this.nickName = nickName;
        setPhoneNumber(phoneNumber);  // 전화번호 검증 포함
        this.role = role != null ? role : Role.ROLE_ADMIN;  // 기본 역할 설정
    }

    // 관리자 정보 업데이트
    public void updateAdminInfo(String password, String nickName, String phoneNumber, PasswordUtil passwordUtil) {
        setPassword(password);
        this.nickName = nickName;
        setPhoneNumber(phoneNumber);
    }

    // 비밀번호 설정 (암호화 포함)
    private void setPassword(String password) {
        validatePasswordStrength(password);
        this.password = password;
    }

    // 비밀번호 재설정 로직 추가
    public void resetPassword(String newPassword, PasswordUtil passwordUtil) {
        setPassword(newPassword);
        encryptPassword(passwordUtil);
    }

    // 비밀번호 암호화 처리
    public void encryptPassword(PasswordUtil passwordUtil) {
        this.password = passwordUtil.encodePassword(this.password);
    }

    // 전화번호 검증 및 설정
    private void setPhoneNumber(String phoneNumber) {
        validatePhoneNumber(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    // Role 변경
    public void changeRole(Role newRole) {
        if (newRole != null) {
            this.role = newRole;
        } else {
            throw new IllegalArgumentException("역할(Role)은 null일 수 없습니다.");
        }
    }

    // 비밀번호 강도 검증 (비즈니스 규칙)
    private void validatePasswordStrength(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 최소 8자 이상이어야 합니다.");
        }
        if (!password.matches(".*[A-Z].*") || !password.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("비밀번호는 대문자와 숫자를 포함해야 합니다.");
        }
    }

    // 전화번호 유효성 검증 (비즈니스 규칙)
    private void validatePhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("\\d{2,3}-\\d{3,4}-\\d{4}")) {
            throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다. 예시: 010-123-4567 또는 010-1234-5678");
        }
    }

    // Getter 메서드
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    // Role이 있는지 확인하는 메서드 추가 (필요한 경우)
    public boolean hasRole() {
        return this.role != null;
    }
}
