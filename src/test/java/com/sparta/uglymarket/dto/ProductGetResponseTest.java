//package com.sparta.uglymarket.dto;
//
//import com.sparta.uglymarket.entity.ProductEntity;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//public class ProductGetResponseTest {
//
//    @Test
//    void testDefaultConstructorAndGetters() {
//        // given
//        ProductGetResponse response = new ProductGetResponse();
//
//        // then
//        assertNull(response.getMessage());
//        assertNull(response.getProduct());
//    }
//
//    @Test
//    void testParameterizedConstructorAndGetters() {
//        // given
//        String expectedMessage = "Product retrieved successfully";
//        ProductEntity expectedProduct = new ProductEntity(); // Assuming ProductEntity has a default constructor
//
//        // when
//        ProductGetResponse response = new ProductGetResponse(expectedMessage, expectedProduct);
//
//        // then
//        assertNotNull(response);
//        assertEquals(expectedMessage, response.getMessage());
//        assertEquals(expectedProduct, response.getProduct());
//    }
//}
