package com.sparta.uglymarket.service;

import com.sparta.uglymarket.entity.AdminEntity;
import com.sparta.uglymarket.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminEntity admin = adminRepository.findByPhoneNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("잘못된 전화번호 또는 비밀번호"));

        return User.builder()
                .username(admin.getPhoneNumber())
                .password(admin.getPassword())
                .roles("ADMIN")
                .build();
    }
}



