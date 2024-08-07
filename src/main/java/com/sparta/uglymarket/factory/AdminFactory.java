package com.sparta.uglymarket.factory;

import com.sparta.uglymarket.dto.AdminRegisterRequest;
import com.sparta.uglymarket.entity.AdminEntity;
import com.sparta.uglymarket.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class AdminFactory {

    public AdminEntity createAdmin(AdminRegisterRequest adminRegisterRequest) {
        return new AdminEntity(
                null,
                adminRegisterRequest.getPhoneNumber(),
                adminRegisterRequest.getPassword(),
                Role.ROLE_ADMIN,
                adminRegisterRequest.getFarmName(),
                adminRegisterRequest.getIntroMessage(),
                adminRegisterRequest.getProfileImageUrl(),
                adminRegisterRequest.getLeaderName(),
                adminRegisterRequest.getBusinessId(),
                adminRegisterRequest.getOpeningDate(),
                adminRegisterRequest.getMinOrderAmount()
        );
    }
}
