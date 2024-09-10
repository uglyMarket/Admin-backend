//package com.sparta.uglymarket.entity;
//
//import com.sparta.uglymarket.dto.AdminUpdateRequest;
//import com.sparta.uglymarket.exception.CustomException;
//import com.sparta.uglymarket.exception.ErrorMsg;
//import com.sparta.uglymarket.repository.AdminRepository;
//import com.sparta.uglymarket.service.AdminService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//class AdminEntityTest {
//
//    @Mock
//    private AdminRepository adminRepository;
//
//    @InjectMocks
//    private AdminService adminService;
//
//    private AdminEntity adminEntity;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        adminEntity = new AdminEntity(
//                1L, "010-1234-5678", "password",
//                Role.ROLE_ADMIN, "Farm Name",
//                "Intro Message", "http://example.com/profile.jpg",
//                "Jane Doe", "1234567890",
//                "2023-01-01", 10000L);
//    }
//    @Test
//    void updateAdmin() {
//        // given
//        Long id = 1L;
//        AdminUpdateRequest request = mock(AdminUpdateRequest.class);
//        when(request.getPhoneNumber()).thenReturn("010-9876-5432");
//        when(request.getPassword()).thenReturn("newpassword");
//        when(request.getFarmName()).thenReturn("Updated Farm");
//        when(request.getIntroMessage()).thenReturn("Updated Message");
//        when(request.getProfileImageUrl()).thenReturn("http://example.com/updated.jpg");
//        when(request.getLeaderName()).thenReturn("John Doe");
//        when(request.getBusinessId()).thenReturn("0987654321");
//        when(request.getOpeningDate()).thenReturn("2024-01-01");
//        when(request.getMinOrderAmount()).thenReturn(20000L);
//
//        AdminEntity adminEntity = new AdminEntity(
//                id, "010-1234-5678", "password",
//                Role.ROLE_ADMIN, "Farm Name",
//                "Intro Message", "http://example.com/profile.jpg",
//                "Jane Doe", "1234567890",
//                "2023-01-01", 10000L);
//
//        when(adminRepository.findById(eq(id))).thenReturn(Optional.of(adminEntity));
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());
//
//        // when
//        adminService.updateAdmin(id, request);
//
//        // then
//        assertEquals("010-9876-5432", adminEntity.getPhoneNumber());
//        assertEquals("newpassword", adminEntity.getPassword());
//        assertEquals("Updated Farm", adminEntity.getFarmName());
//        assertEquals("Updated Message", adminEntity.getIntroMessage());
//        assertEquals("http://example.com/updated.jpg", adminEntity.getProfileImageUrl());
//        assertEquals("John Doe", adminEntity.getLeaderName());
//        assertEquals("0987654321", adminEntity.getBusinessId());
//        assertEquals("2024-01-01", adminEntity.getOpeningDate());
//        assertEquals(20000L, adminEntity.getMinOrderAmount());
//
//        verify(adminRepository, times(1)).save(adminEntity);
//    }
//
//    @Test
//    void updateAdmin_throwsException_whenAdminNotFound() {
//        // given
//        Long id = 1L;
//        AdminUpdateRequest request = mock(AdminUpdateRequest.class);
//        when(adminRepository.findById(eq(id))).thenReturn(Optional.empty());
//
//        // when & then
//        CustomException exception = assertThrows(CustomException.class, () -> adminService.updateAdmin(id, request));
//        assertEquals(ErrorMsg.ADMIN_NOT_FOUND.getHttpStatus(), exception.getHttpStatus());
//    }
//    @Test
//    void testSetRole() {
//        // given
//        Role newRole = Role.ROLE_ADMIN;
//
//        // when
//        adminEntity.setRole(newRole);
//
//        // then
//        assertEquals(newRole, adminEntity.getRole());
//    }
//
//    @Test
//    void testSetPassword() {
//        // given
//        String newPassword = "newPassword";
//
//        // when
//        adminEntity.setPassword(newPassword);
//
//        // then
//        assertEquals(newPassword, adminEntity.getPassword());
//    }
//}
