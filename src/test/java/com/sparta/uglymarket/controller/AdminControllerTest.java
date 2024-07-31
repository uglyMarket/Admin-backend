//package com.sparta.uglymarket.controller;
//
//import com.sparta.uglymarket.dto.AdminRegisterRequest;
//import com.sparta.uglymarket.dto.AdminRegisterResponse;
//import com.sparta.uglymarket.dto.AdminInfoResponse;
//import com.sparta.uglymarket.dto.AdminUpdateRequest;
//import com.sparta.uglymarket.dto.AdminUpdateResponse;
//import com.sparta.uglymarket.service.AdminService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//public class AdminControllerTest {
//
//    @Mock
//    private AdminService adminService;
//
//    @InjectMocks
//    private AdminController adminController;
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
//        when(adminService.register(any(AdminRegisterRequest.class))).thenReturn(new AdminRegisterResponse("success", "회원가입이 성공적으로 완료되었습니다."));
//
//        AdminRegisterResponse response = adminController.register(adminRequest);
//
//        assertEquals("success", response.getStatus());
//        assertEquals("회원가입이 성공적으로 완료되었습니다.", response.getMessage());
//    }
//
//    @Test
//    public void testRegisterFailureAllFieldsMissing() {
//        AdminRegisterRequest adminRequest = new AdminRegisterRequest();
//
//        AdminRegisterResponse response = adminController.register(adminRequest);
//
//        assertEquals("error", response.getStatus());
//        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
//    }
//
//    @Test
//    public void testRegisterFailurePhoneNumberExists() {
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
//        when(adminService.register(any(AdminRegisterRequest.class))).thenReturn(new AdminRegisterResponse("error", "전화번호가 이미 존재합니다."));
//
//        AdminRegisterResponse response = adminController.register(adminRequest);
//
//        assertEquals("error", response.getStatus());
//        assertEquals("전화번호가 이미 존재합니다.", response.getMessage());
//    }
//
//    @Test
//    public void testRegisterFailureFarmNameExists() {
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
//        when(adminService.register(any(AdminRegisterRequest.class))).thenReturn(new AdminRegisterResponse("error", "농장 이름이 이미 존재합니다."));
//
//        AdminRegisterResponse response = adminController.register(adminRequest);
//
//        assertEquals("error", response.getStatus());
//        assertEquals("농장 이름이 이미 존재합니다.", response.getMessage());
//    }
//
//    @Test
//    public void testRegisterFailureMissingFarmName() {
//        AdminRegisterRequest adminRequest = new AdminRegisterRequest();
//        adminRequest.setIntroMessage("Welcome to MyFarm");
//        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
//        adminRequest.setPhoneNumber("01012345678");
//        adminRequest.setLeaderName("John Doe");
//        adminRequest.setBusinessId("1234567890");
//        adminRequest.setOpeningDate("2024-01-01");
//        adminRequest.setMinOrderAmount(1000L);
//        adminRequest.setPassword("securepassword");
//
//        AdminRegisterResponse response = adminController.register(adminRequest);
//
//        assertEquals("error", response.getStatus());
//        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
//    }
//
//    @Test
//    public void testRegisterFailureMissingIntroMessage() {
//        AdminRegisterRequest adminRequest = new AdminRegisterRequest();
//        adminRequest.setFarmName("MyFarm");
//        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
//        adminRequest.setPhoneNumber("01012345678");
//        adminRequest.setLeaderName("John Doe");
//        adminRequest.setBusinessId("1234567890");
//        adminRequest.setOpeningDate("2024-01-01");
//        adminRequest.setMinOrderAmount(1000L);
//        adminRequest.setPassword("securepassword");
//
//        AdminRegisterResponse response = adminController.register(adminRequest);
//
//        assertEquals("error", response.getStatus());
//        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
//    }
//
//    @Test
//    public void testRegisterFailureMissingProfileImageUrl() {
//        AdminRegisterRequest adminRequest = new AdminRegisterRequest();
//        adminRequest.setFarmName("MyFarm");
//        adminRequest.setIntroMessage("Welcome to MyFarm");
//        adminRequest.setPhoneNumber("01012345678");
//        adminRequest.setLeaderName("John Doe");
//        adminRequest.setBusinessId("1234567890");
//        adminRequest.setOpeningDate("2024-01-01");
//        adminRequest.setMinOrderAmount(1000L);
//        adminRequest.setPassword("securepassword");
//
//        AdminRegisterResponse response = adminController.register(adminRequest);
//
//        assertEquals("error", response.getStatus());
//        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
//    }
//
//    @Test
//    public void testRegisterFailureMissingPhoneNumber() {
//        AdminRegisterRequest adminRequest = new AdminRegisterRequest();
//        adminRequest.setFarmName("MyFarm");
//        adminRequest.setIntroMessage("Welcome to MyFarm");
//        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
//        adminRequest.setLeaderName("John Doe");
//        adminRequest.setBusinessId("1234567890");
//        adminRequest.setOpeningDate("2024-01-01");
//        adminRequest.setMinOrderAmount(1000L);
//        adminRequest.setPassword("securepassword");
//
//        AdminRegisterResponse response = adminController.register(adminRequest);
//
//        assertEquals("error", response.getStatus());
//        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
//    }
//
//    @Test
//    public void testRegisterFailureMissingLeaderName() {
//        AdminRegisterRequest adminRequest = new AdminRegisterRequest();
//        adminRequest.setFarmName("MyFarm");
//        adminRequest.setIntroMessage("Welcome to MyFarm");
//        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
//        adminRequest.setPhoneNumber("01012345678");
//        adminRequest.setBusinessId("1234567890");
//        adminRequest.setOpeningDate("2024-01-01");
//        adminRequest.setMinOrderAmount(1000L);
//        adminRequest.setPassword("securepassword");
//
//        AdminRegisterResponse response = adminController.register(adminRequest);
//
//        assertEquals("error", response.getStatus());
//        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
//    }
//
//    @Test
//    public void testRegisterFailureMissingBusinessId() {
//        AdminRegisterRequest adminRequest = new AdminRegisterRequest();
//        adminRequest.setFarmName("MyFarm");
//        adminRequest.setIntroMessage("Welcome to MyFarm");
//        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
//        adminRequest.setPhoneNumber("01012345678");
//        adminRequest.setLeaderName("John Doe");
//        adminRequest.setOpeningDate("2024-01-01");
//        adminRequest.setMinOrderAmount(1000L);
//        adminRequest.setPassword("securepassword");
//
//        AdminRegisterResponse response = adminController.register(adminRequest);
//
//        assertEquals("error", response.getStatus());
//        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
//    }
//
//    @Test
//    public void testRegisterFailureMissingOpeningDate() {
//        AdminRegisterRequest adminRequest = new AdminRegisterRequest();
//        adminRequest.setFarmName("MyFarm");
//        adminRequest.setIntroMessage("Welcome to MyFarm");
//        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
//        adminRequest.setPhoneNumber("01012345678");
//        adminRequest.setLeaderName("John Doe");
//        adminRequest.setBusinessId("1234567890");
//        adminRequest.setMinOrderAmount(1000L);
//        adminRequest.setPassword("securepassword");
//
//        AdminRegisterResponse response = adminController.register(adminRequest);
//
//        assertEquals("error", response.getStatus());
//        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
//    }
//
//    @Test
//    public void testRegisterFailureMissingMinOrderAmount() {
//        AdminRegisterRequest adminRequest = new AdminRegisterRequest();
//        adminRequest.setFarmName("MyFarm");
//        adminRequest.setIntroMessage("Welcome to MyFarm");
//        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
//        adminRequest.setPhoneNumber("01012345678");
//        adminRequest.setLeaderName("John Doe");
//        adminRequest.setBusinessId("1234567890");
//        adminRequest.setOpeningDate("2024-01-01");
//        adminRequest.setPassword("securepassword");
//
//        AdminRegisterResponse response = adminController.register(adminRequest);
//
//        assertEquals("error", response.getStatus());
//        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
//    }
//
//    @Test
//    public void testRegisterFailureMissingPassword() {
//        AdminRegisterRequest adminRequest = new AdminRegisterRequest();
//        adminRequest.setFarmName("MyFarm");
//        adminRequest.setIntroMessage("Welcome to MyFarm");
//        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
//        adminRequest.setPhoneNumber("01012345678");
//        adminRequest.setLeaderName("John Doe");
//        adminRequest.setBusinessId("1234567890");
//        adminRequest.setOpeningDate("2024-01-01");
//        adminRequest.setMinOrderAmount(1000L);
//
//        AdminRegisterResponse response = adminController.register(adminRequest);
//
//        assertEquals("error", response.getStatus());
//        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
//    }
//}
