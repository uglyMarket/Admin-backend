package com.sparta.uglymarket.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdminLoginResponseTest {

    @Test
    void testAdminLoginResponseConstructorAndGetter() {
        // given
        String expectedMessage = "Login successful";

        // when
        AdminLoginResponse response = new AdminLoginResponse(expectedMessage);

        // then
        assertNotNull(response);
        assertEquals(expectedMessage, response.getMessage());
    }
}
