//package com.sparta.uglymarket.entity;
//
//import com.sparta.uglymarket.dto.AdminRegisterRequest;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class AdminEntityTest {
//
//    @Test
//    public void testAdminEntityConstructor() {
//        AdminRegisterRequest adminRequest = new AdminRegisterRequest();
//        adminRequest.setFarmName("MyFarm");
//        adminRequest.setIntroMessage("Welcome to MyFarm");
//        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
//        adminRequest.setPhoneNumber("01012345678");
//        adminRequest.setLeaderName("John Doe");
//        adminRequest.setBusinessId("1234567890");
//        adminRequest.setOpeningDate("2024-01-01");
//        adminRequest.setMinOrderAmount(1000L);
//        adminRequest.setPassword("securepassword");
//
//        AdminEntity adminEntity = new AdminEntity(adminRequest);
//
//        assertEquals("MyFarm", adminEntity.getFarmName());
//        assertEquals("Welcome to MyFarm", adminEntity.getIntroMessage());
//        assertEquals("http://example.com/profile.jpg", adminEntity.getProfileImageUrl());
//        assertEquals("01012345678", adminEntity.getPhoneNumber());
//        assertEquals("John Doe", adminEntity.getLeaderName());
//        assertEquals("1234567890", adminEntity.getBusinessId());
//        assertEquals("2024-01-01", adminEntity.getOpeningDate());
//        assertEquals(1000L, adminEntity.getMinOrderAmount());
//        assertEquals("securepassword", adminEntity.getPassword());
//    }
//
//    @Test
//    public void testNoArgsConstructor() {
//        AdminEntity adminEntity = new AdminEntity();
//
//        // 기본 생성자가 정상적으로 호출되었는지 확인
//        assertEquals(null, adminEntity.getFarmName());
//        assertEquals(null, adminEntity.getIntroMessage());
//        assertEquals(null, adminEntity.getProfileImageUrl());
//        assertEquals(null, adminEntity.getPhoneNumber());
//        assertEquals(null, adminEntity.getLeaderName());
//        assertEquals(null, adminEntity.getBusinessId());
//        assertEquals(null, adminEntity.getOpeningDate());
//        assertEquals(null, adminEntity.getMinOrderAmount());
//        assertEquals(null, adminEntity.getPassword());
//    }
//}
