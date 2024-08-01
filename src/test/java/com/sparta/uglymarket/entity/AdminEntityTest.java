package com.sparta.uglymarket.entity;

import com.sparta.uglymarket.dto.AdminRegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminEntityTest {

    private AdminRegisterRequest adminRegisterRequest;
    private AdminEntity adminEntity;

    @BeforeEach
    void setUp() {
        adminRegisterRequest = mock(AdminRegisterRequest.class);
        when(adminRegisterRequest.getPhoneNumber()).thenReturn("010-1234-5678");
        when(adminRegisterRequest.getPassword()).thenReturn("password");
        when(adminRegisterRequest.getFarmName()).thenReturn("My Farm");
        when(adminRegisterRequest.getIntroMessage()).thenReturn("Welcome to My Farm");
        when(adminRegisterRequest.getProfileImageUrl()).thenReturn("http://example.com/profile.jpg");
        when(adminRegisterRequest.getLeaderName()).thenReturn("John Doe");
        when(adminRegisterRequest.getBusinessId()).thenReturn("1234567890");
        when(adminRegisterRequest.getOpeningDate()).thenReturn("2023-01-01");
        when(adminRegisterRequest.getMinOrderAmount()).thenReturn(10000L);

        adminEntity = new AdminEntity(adminRegisterRequest);
    }

    @Test
    void testAdminEntityConstructor() {
        // then
        assertEquals(adminRegisterRequest.getPhoneNumber(), adminEntity.getPhoneNumber());
        assertEquals(adminRegisterRequest.getPassword(), adminEntity.getPassword());
        assertEquals(adminRegisterRequest.getFarmName(), adminEntity.getFarmName());
        assertEquals(adminRegisterRequest.getIntroMessage(), adminEntity.getIntroMessage());
        assertEquals(adminRegisterRequest.getProfileImageUrl(), adminEntity.getProfileImageUrl());
        assertEquals(adminRegisterRequest.getLeaderName(), adminEntity.getLeaderName());
        assertEquals(adminRegisterRequest.getBusinessId(), adminEntity.getBusinessId());
        assertEquals(adminRegisterRequest.getOpeningDate(), adminEntity.getOpeningDate());
        assertEquals(adminRegisterRequest.getMinOrderAmount(), adminEntity.getMinOrderAmount());
        assertEquals(Role.ROLE_ADMIN, adminEntity.getRole());
    }

    @Test
    void testUpdate() {
        // given
        AdminRegisterRequest updatedRequest = mock(AdminRegisterRequest.class);
        when(updatedRequest.getPhoneNumber()).thenReturn("010-9876-5432");
        when(updatedRequest.getPassword()).thenReturn("newpassword");
        when(updatedRequest.getFarmName()).thenReturn("New Farm");
        when(updatedRequest.getIntroMessage()).thenReturn("Welcome to New Farm");
        when(updatedRequest.getProfileImageUrl()).thenReturn("http://example.com/newprofile.jpg");
        when(updatedRequest.getLeaderName()).thenReturn("Jane Doe");
        when(updatedRequest.getBusinessId()).thenReturn("0987654321");
        when(updatedRequest.getOpeningDate()).thenReturn("2024-01-01");
        when(updatedRequest.getMinOrderAmount()).thenReturn(20000L);

        // when
        adminEntity.update(updatedRequest);

        // then
        assertEquals(updatedRequest.getPhoneNumber(), adminEntity.getPhoneNumber());
        assertEquals(updatedRequest.getPassword(), adminEntity.getPassword());
        assertEquals(updatedRequest.getFarmName(), adminEntity.getFarmName());
        assertEquals(updatedRequest.getIntroMessage(), adminEntity.getIntroMessage());
        assertEquals(updatedRequest.getProfileImageUrl(), adminEntity.getProfileImageUrl());
        assertEquals(updatedRequest.getLeaderName(), adminEntity.getLeaderName());
        assertEquals(updatedRequest.getBusinessId(), adminEntity.getBusinessId());
        assertEquals(updatedRequest.getOpeningDate(), adminEntity.getOpeningDate());
        assertEquals(updatedRequest.getMinOrderAmount(), adminEntity.getMinOrderAmount());
    }

    @Test
    void testSetRole() {
        // when
        adminEntity.setRole(Role.ROLE_ADMIN);

        // then
        assertEquals(Role.ROLE_ADMIN, adminEntity.getRole());
    }

    @Test
    void testSetPassword() {
        // when
        adminEntity.setPassword("newpassword");

        // then
        assertEquals("newpassword", adminEntity.getPassword());
    }
}
