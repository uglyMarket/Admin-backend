//package com.sparta.uglymarket.entity;
//
//import com.sparta.uglymarket.dto.AdminUpdateRequest;
//import com.sparta.uglymarket.dto.AdminUpdateResponse;
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
//        adminEntity = mock(AdminEntity.class);
//
//        when(adminEntity.getPhoneNumber()).thenReturn("010-1234-5678");
//        when(adminEntity.getPassword()).thenReturn("password");
//        when(adminEntity.getRole()).thenReturn(Role.ROLE_ADMIN);
//        when(adminEntity.getFarmName()).thenReturn("Farm Name");
//        when(adminEntity.getIntroMessage()).thenReturn("Intro Message");
//        when(adminEntity.getProfileImageUrl()).thenReturn("http://example.com/profile.jpg");
//        when(adminEntity.getLeaderName()).thenReturn("Jane Doe");
//        when(adminEntity.getBusinessId()).thenReturn("1234567890");
//        when(adminEntity.getOpeningDate()).thenReturn("2023-01-01");
//        when(adminEntity.getMinOrderAmount()).thenReturn(10000L);
//    }
//
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
//        when(adminRepository.findById(eq(id))).thenReturn(Optional.of(adminEntity));
//        when(adminRepository.findByPhoneNumber(anyString())).thenReturn(Optional.empty());
//
//        // when
//        adminService.updateAdmin(id, request);
//
//        // then
//        verify(adminEntity).update(request);
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
//}
