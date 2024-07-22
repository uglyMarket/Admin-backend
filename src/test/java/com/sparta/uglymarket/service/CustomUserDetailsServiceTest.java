package com.sparta.uglymarket.service;

import com.sparta.uglymarket.entity.AdminEntity;
import com.sparta.uglymarket.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CustomUserDetailsServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsernameSuccess() {
        AdminEntity admin = new AdminEntity();
        ReflectionTestUtils.setField(admin, "phoneNumber", "01012345678");
        ReflectionTestUtils.setField(admin, "password", "securepassword");

        when(adminRepository.findByPhoneNumber("01012345678")).thenReturn(Optional.of(admin));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("01012345678");

        assertEquals("01012345678", userDetails.getUsername());
        assertEquals("securepassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")));
    }


    @Test
    public void testLoadUserByUsernameFailure() {
        when(adminRepository.findByPhoneNumber("01012345678")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("01012345678");
        });
    }
}
