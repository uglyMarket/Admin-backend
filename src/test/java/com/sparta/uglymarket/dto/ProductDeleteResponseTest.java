package com.sparta.uglymarket.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProductDeleteResponseTest {

    @Test
    void testDefaultConstructorAndGetters() {
        // given
        ProductDeleteResponse response = new ProductDeleteResponse();

        // then
        assertNull(response.getMessage());
    }

    @Test
    void testParameterizedConstructorAndGetters() {
        // given
        String expectedMessage = "Product deleted successfully";

        // when
        ProductDeleteResponse response = new ProductDeleteResponse(expectedMessage);

        // then
        assertNotNull(response);
        assertEquals(expectedMessage, response.getMessage());
    }
}
