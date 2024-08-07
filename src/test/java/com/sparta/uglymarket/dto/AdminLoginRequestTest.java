package com.sparta.uglymarket.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdminLoginRequestTest {

    @Test
    void testDefaultConstructorAndGetters() {
        // given
        AdminLoginRequest request = new AdminLoginRequest();

        // then
        assertNull(request.getPhoneNumber());
        assertNull(request.getPassword());
    }
}
