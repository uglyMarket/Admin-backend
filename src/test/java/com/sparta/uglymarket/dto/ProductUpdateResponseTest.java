package com.sparta.uglymarket.dto;

import com.sparta.uglymarket.entity.ProductEntity;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProductUpdateResponseTest {

    @Test
    void testDefaultConstructorAndGetters() {
        // given
        ProductUpdateResponse response = new ProductUpdateResponse();

        // then
        assertNull(response.getMessage());
        assertNull(response.getProduct());
    }

    @Test
    void testParameterizedConstructorAndGetters() throws NoSuchFieldException, IllegalAccessException {
        // given
        String expectedMessage = "Product updated successfully";
        ProductEntity expectedProduct = new ProductEntity();
        setFieldValue(expectedProduct, "id", 1L);
        setFieldValue(expectedProduct, "title", "Updated Product Title");
        setFieldValue(expectedProduct, "content", "Updated Product Content");
        setFieldValue(expectedProduct, "price", 2000L);
        setFieldValue(expectedProduct, "stock", 20L);
        setFieldValue(expectedProduct, "imageUrl", "http://example.com/updated-image.jpg");
        setFieldValue(expectedProduct, "category", "Updated Category");

        // when
        ProductUpdateResponse response = new ProductUpdateResponse(expectedMessage, expectedProduct);

        // then
        assertNotNull(response);
        assertEquals(expectedMessage, response.getMessage());
        assertEquals(expectedProduct, response.getProduct());
        assertEquals(1L, response.getProduct().getId());
        assertEquals("Updated Product Title", response.getProduct().getTitle());
        assertEquals("Updated Product Content", response.getProduct().getContent());
        assertEquals(2000L, response.getProduct().getPrice());
        assertEquals(20L, response.getProduct().getStock());
        assertEquals("http://example.com/updated-image.jpg", response.getProduct().getImageUrl());
        assertEquals("Updated Category", response.getProduct().getCategory());
    }

    private void setFieldValue(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }
}
