package com.sparta.uglymarket.dto;

import com.sparta.uglymarket.entity.ProductEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProductCreateResponseTest {

    @Test
    void testDefaultConstructorAndGetters() {
        // given
        ProductCreateResponse response = new ProductCreateResponse();

        // then
        assertNull(response.getMessage());
        assertNull(response.getProduct());
    }

    @Test
    void testParameterizedConstructorAndGetters() {
        // given
        String expectedMessage = "Product created successfully";
        ProductEntity expectedProduct = new ProductEntity(); // Assuming ProductEntity has a default constructor

        // when
        ProductCreateResponse response = new ProductCreateResponse(expectedMessage, expectedProduct);

        // then
        assertNotNull(response);
        assertEquals(expectedMessage, response.getMessage());
        assertEquals(expectedProduct, response.getProduct());
    }
}
