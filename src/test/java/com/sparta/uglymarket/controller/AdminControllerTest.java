//package com.sparta.uglymarket.controller;
//
//import com.sparta.uglymarket.dto.*;
//import com.sparta.uglymarket.service.AdminService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.doReturn;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class AdminControllerTest {
//
//    @Mock
//    private AdminService adminService;
//
//    @InjectMocks
//    private AdminController adminController;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
//    }
//
//    @Test
//    public void testRegister() throws Exception {
//        AdminRegisterResponse mockResponse = new AdminRegisterResponse("User registered successfully", "ROLE_USER");
//        when(adminService.register(any(AdminRegisterRequest.class))).thenReturn(mockResponse);
//
//        mockMvc.perform(post("/api/admin/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"password\": \"password\", \"phoneNumber\": \"123-456-7890\", \"farmName\": \"Farm Name\", \"introMessage\": \"Intro\", \"profileImageUrl\": \"http://example.com/profile.jpg\", \"minOrderAmount\": 1000, \"businessId\": \"1234567890\", \"openingDate\": \"2024-01-01\", \"leaderName\": \"Leader Name\"}"))
//                .andExpect(status().isCreated())
//                .andExpect(content().json("{\"message\":\"User registered successfully\", \"role\":\"ROLE_USER\"}"));
//    }
//
//    @Test
//    public void testUpdateAdmin() throws Exception {
//        AdminUpdateResponse mockResponse = new AdminUpdateResponse("User updated successfully", "ROLE_USER");
//        when(adminService.updateAdmin(anyLong(), any(AdminUpdateRequest.class))).thenReturn(mockResponse);
//
//        mockMvc.perform(put("/api/admin/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"password\": \"newpassword\", \"phoneNumber\": \"123-456-7890\", \"farmName\": \"New Farm Name\", \"introMessage\": \"Intro\", \"profileImageUrl\": \"http://example.com/profile.jpg\", \"minOrderAmount\": 1000, \"businessId\": \"1234567890\", \"openingDate\": \"2024-01-01\", \"leaderName\": \"Leader Name\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().json("{\"message\":\"User updated successfully\", \"role\":\"ROLE_USER\"}"));
//    }
//
//    @Test
//    public void testLogin() throws Exception {
//        AdminLoginResponse loginResponse = new AdminLoginResponse("valid-token");
//        ResponseEntity<AdminLoginResponse> responseEntity = ResponseEntity.ok(loginResponse);
//
//        doReturn(responseEntity).when(adminService).login(any(AdminLoginRequest.class));
//
//        mockMvc.perform(post("/api/admin/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"username\": \"username\", \"password\": \"password\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().json("{\"message\":\"valid-token\"}"));
//    }
//
//
//
//    @Test
//    public void testLogout() throws Exception {
//        LogoutResponse mockResponse = new LogoutResponse("Logout successful");
//        when(adminService.logout(anyString())).thenReturn(mockResponse);
//
//        mockMvc.perform(post("/api/admin/logout")
//                        .header("Authorization", "Bearer token"))
//                .andExpect(status().isOk())
//                .andExpect(content().json("{\"message\":\"Logout successful\"}"));
//    }
//
//    @Test
//    public void testUpdateAdminWithInvalidData() throws Exception {
//        mockMvc.perform(put("/api/admin/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"password\": \"123\", \"phoneNumber\": \"123\", \"farmName\": \"\"}"))
//                .andExpect(status().isBadRequest());
//    }
//}
