//package com.sparta.uglymarket.service;
//
//import com.sparta.uglymarket.dto.AdminRegisterRequest;
//import com.sparta.uglymarket.dto.AdminResponse;
//import com.sparta.uglymarket.entity.AdminEntity;
//import com.sparta.uglymarket.repository.AdminRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//public class AdminServiceTest {
//
//    @Mock
//    private AdminRepository adminRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private AdminService adminService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testRegisterSuccess() {
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
//        when(adminRepository.findByPhoneNumber(any(String.class))).thenReturn(Optional.empty());
//        when(adminRepository.findByFarmName(any(String.class))).thenReturn(Optional.empty());
//        when(passwordEncoder.encode(any(String.class))).thenReturn("hashedpassword");
//
//        AdminResponse response = adminService.register(adminRequest);
//
//        assertEquals("success", response.getStatus());
//        assertEquals("회원가입이 성공적으로 완료되었습니다.", response.getMessage());
//    }
//
//    @Test
//    public void testRegisterFailurePhoneNumberExists() {
//        AdminRegisterRequest adminRequest = new AdminRegisterRequest();
//        adminRequest.setPhoneNumber("01012345678");
//
//        when(adminRepository.findByPhoneNumber(any(String.class))).thenReturn(Optional.of(new AdminEntity()));
//
//        AdminResponse response = adminService.register(adminRequest);
//
//        assertEquals("error", response.getStatus());
//        assertEquals("전화번호가 이미 존재합니다.", response.getMessage());
//    }
//
//    @Test
//    public void testRegisterFailureFarmNameExists() {
//        AdminRegisterRequest adminRequest = new AdminRegisterRequest();
//        adminRequest.setFarmName("MyFarm");
//
//        when(adminRepository.findByFarmName(any(String.class))).thenReturn(Optional.of(new AdminEntity()));
//
//        AdminResponse response = adminService.register(adminRequest);
//
//        assertEquals("error", response.getStatus());
//        assertEquals("농장 이름이 이미 존재합니다.", response.getMessage());
//    }
//}
