package com.sparta.uglymarket.controller;

import com.sparta.uglymarket.dto.AdminRequest;
import com.sparta.uglymarket.dto.AdminResponse;
import com.sparta.uglymarket.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService; // AdminService 객체를 주입받습니다.

    // 관리자 회원 가입
    @PostMapping("/register")
    public AdminResponse register(@RequestBody AdminRequest adminRequest) {
        // 필수 항목이 모두 입력되었는지 확인합니다.
        if (adminRequest.getFarmName() == null || adminRequest.getIntroMessage() == null ||
                adminRequest.getProfileImageUrl() == null || adminRequest.getPhoneNumber() == null ||
                adminRequest.getLeaderName() == null || adminRequest.getBusinessId() == null ||
                adminRequest.getOpeningDate() == null || adminRequest.getMinOrderAmount() == null ||
                adminRequest.getPassword() == null) {
            return new AdminResponse("error", "모든 항목을 입력해야 합니다.");
        }
        // 필수 항목이 모두 입력된 경우, AdminService를 통해 회원가입을 처리합니다.
        return adminService.register(adminRequest);
    }




//    SecurityConfig 클래스에서 설정하지 않고 Controller 에서 작성하는 방법도 있다.
//
//    // 로그인 성공 후 리디렉션될 엔드포인트
//    @GetMapping("/success")
//    public String loginSuccess() {
//        return "로그인 성공";
//    }
//
//    // 로그아웃 성공 후 리디렉션될 엔드포인트
//    @GetMapping("/logout-success")
//    public String logoutSuccess() {
//        return "로그아웃 성공";
//    }
}


