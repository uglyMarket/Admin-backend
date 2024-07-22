package com.sparta.uglymarket.controller;

import com.sparta.uglymarket.dto.AdminRequest;
import com.sparta.uglymarket.dto.AdminResponse;
import com.sparta.uglymarket.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterSuccess() {
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setFarmName("MyFarm");
        adminRequest.setIntroMessage("Welcome to MyFarm");
        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
        adminRequest.setPhoneNumber("01012345678");
        adminRequest.setLeaderName("John Doe");
        adminRequest.setBusinessId("1234567890");
        adminRequest.setOpeningDate("2024-01-01");
        adminRequest.setMinOrderAmount(1000L);
        adminRequest.setPassword("securepassword");

        when(adminService.register(any(AdminRequest.class))).thenReturn(new AdminResponse("success", "회원가입이 성공적으로 완료되었습니다."));

        AdminResponse response = adminController.register(adminRequest);

        assertEquals("success", response.getStatus());
        assertEquals("회원가입이 성공적으로 완료되었습니다.", response.getMessage());
    }

    @Test
    public void testRegisterFailureAllFieldsMissing() {
        AdminRequest adminRequest = new AdminRequest();

        AdminResponse response = adminController.register(adminRequest);

        assertEquals("error", response.getStatus());
        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
    }

    @Test
    public void testRegisterFailurePhoneNumberExists() {
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setFarmName("MyFarm");
        adminRequest.setIntroMessage("Welcome to MyFarm");
        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
        adminRequest.setPhoneNumber("01012345678");
        adminRequest.setLeaderName("John Doe");
        adminRequest.setBusinessId("1234567890");
        adminRequest.setOpeningDate("2024-01-01");
        adminRequest.setMinOrderAmount(1000L);
        adminRequest.setPassword("securepassword");

        when(adminService.register(any(AdminRequest.class))).thenReturn(new AdminResponse("error", "전화번호가 이미 존재합니다."));

        AdminResponse response = adminController.register(adminRequest);

        assertEquals("error", response.getStatus());
        assertEquals("전화번호가 이미 존재합니다.", response.getMessage());
    }

    @Test
    public void testRegisterFailureFarmNameExists() {
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setFarmName("MyFarm");
        adminRequest.setIntroMessage("Welcome to MyFarm");
        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
        adminRequest.setPhoneNumber("01012345678");
        adminRequest.setLeaderName("John Doe");
        adminRequest.setBusinessId("1234567890");
        adminRequest.setOpeningDate("2024-01-01");
        adminRequest.setMinOrderAmount(1000L);
        adminRequest.setPassword("securepassword");

        when(adminService.register(any(AdminRequest.class))).thenReturn(new AdminResponse("error", "농장 이름이 이미 존재합니다."));

        AdminResponse response = adminController.register(adminRequest);

        assertEquals("error", response.getStatus());
        assertEquals("농장 이름이 이미 존재합니다.", response.getMessage());
    }

    @Test
    public void testRegisterFailureMissingFarmName() {
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setIntroMessage("Welcome to MyFarm");
        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
        adminRequest.setPhoneNumber("01012345678");
        adminRequest.setLeaderName("John Doe");
        adminRequest.setBusinessId("1234567890");
        adminRequest.setOpeningDate("2024-01-01");
        adminRequest.setMinOrderAmount(1000L);
        adminRequest.setPassword("securepassword");

        AdminResponse response = adminController.register(adminRequest);

        assertEquals("error", response.getStatus());
        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
    }

    @Test
    public void testRegisterFailureMissingIntroMessage() {
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setFarmName("MyFarm");
        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
        adminRequest.setPhoneNumber("01012345678");
        adminRequest.setLeaderName("John Doe");
        adminRequest.setBusinessId("1234567890");
        adminRequest.setOpeningDate("2024-01-01");
        adminRequest.setMinOrderAmount(1000L);
        adminRequest.setPassword("securepassword");

        AdminResponse response = adminController.register(adminRequest);

        assertEquals("error", response.getStatus());
        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
    }

    @Test
    public void testRegisterFailureMissingProfileImageUrl() {
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setFarmName("MyFarm");
        adminRequest.setIntroMessage("Welcome to MyFarm");
        adminRequest.setPhoneNumber("01012345678");
        adminRequest.setLeaderName("John Doe");
        adminRequest.setBusinessId("1234567890");
        adminRequest.setOpeningDate("2024-01-01");
        adminRequest.setMinOrderAmount(1000L);
        adminRequest.setPassword("securepassword");

        AdminResponse response = adminController.register(adminRequest);

        assertEquals("error", response.getStatus());
        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
    }

    @Test
    public void testRegisterFailureMissingPhoneNumber() {
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setFarmName("MyFarm");
        adminRequest.setIntroMessage("Welcome to MyFarm");
        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
        adminRequest.setLeaderName("John Doe");
        adminRequest.setBusinessId("1234567890");
        adminRequest.setOpeningDate("2024-01-01");
        adminRequest.setMinOrderAmount(1000L);
        adminRequest.setPassword("securepassword");

        AdminResponse response = adminController.register(adminRequest);

        assertEquals("error", response.getStatus());
        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
    }

    @Test
    public void testRegisterFailureMissingLeaderName() {
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setFarmName("MyFarm");
        adminRequest.setIntroMessage("Welcome to MyFarm");
        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
        adminRequest.setPhoneNumber("01012345678");
        adminRequest.setBusinessId("1234567890");
        adminRequest.setOpeningDate("2024-01-01");
        adminRequest.setMinOrderAmount(1000L);
        adminRequest.setPassword("securepassword");

        AdminResponse response = adminController.register(adminRequest);

        assertEquals("error", response.getStatus());
        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
    }

    @Test
    public void testRegisterFailureMissingBusinessId() {
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setFarmName("MyFarm");
        adminRequest.setIntroMessage("Welcome to MyFarm");
        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
        adminRequest.setPhoneNumber("01012345678");
        adminRequest.setLeaderName("John Doe");
        adminRequest.setOpeningDate("2024-01-01");
        adminRequest.setMinOrderAmount(1000L);
        adminRequest.setPassword("securepassword");

        AdminResponse response = adminController.register(adminRequest);

        assertEquals("error", response.getStatus());
        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
    }

    @Test
    public void testRegisterFailureMissingOpeningDate() {
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setFarmName("MyFarm");
        adminRequest.setIntroMessage("Welcome to MyFarm");
        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
        adminRequest.setPhoneNumber("01012345678");
        adminRequest.setLeaderName("John Doe");
        adminRequest.setBusinessId("1234567890");
        adminRequest.setMinOrderAmount(1000L);
        adminRequest.setPassword("securepassword");

        AdminResponse response = adminController.register(adminRequest);

        assertEquals("error", response.getStatus());
        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
    }

    @Test
    public void testRegisterFailureMissingMinOrderAmount() {
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setFarmName("MyFarm");
        adminRequest.setIntroMessage("Welcome to MyFarm");
        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
        adminRequest.setPhoneNumber("01012345678");
        adminRequest.setLeaderName("John Doe");
        adminRequest.setBusinessId("1234567890");
        adminRequest.setOpeningDate("2024-01-01");
        adminRequest.setPassword("securepassword");

        AdminResponse response = adminController.register(adminRequest);

        assertEquals("error", response.getStatus());
        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
    }

    @Test
    public void testRegisterFailureMissingPassword() {
        AdminRequest adminRequest = new AdminRequest();
        adminRequest.setFarmName("MyFarm");
        adminRequest.setIntroMessage("Welcome to MyFarm");
        adminRequest.setProfileImageUrl("http://example.com/profile.jpg");
        adminRequest.setPhoneNumber("01012345678");
        adminRequest.setLeaderName("John Doe");
        adminRequest.setBusinessId("1234567890");
        adminRequest.setOpeningDate("2024-01-01");
        adminRequest.setMinOrderAmount(1000L);

        AdminResponse response = adminController.register(adminRequest);

        assertEquals("error", response.getStatus());
        assertEquals("모든 항목을 입력해야 합니다.", response.getMessage());
    }
}
