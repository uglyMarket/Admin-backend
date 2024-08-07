package com.sparta.uglymarket.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdminUpdateResponseTest {

    @Test
    void testAdminUpdateResponseConstructorAndGetters() {
        // given
        String expectedMessage = "Update successful";
        String expectedRole = "ADMIN";

        // when
        AdminUpdateResponse response = new AdminUpdateResponse(expectedMessage, expectedRole);

        // then
        assertNotNull(response);
        assertEquals(expectedMessage, response.getMessage());
        assertEquals(expectedRole, response.getRole());
    }
}
