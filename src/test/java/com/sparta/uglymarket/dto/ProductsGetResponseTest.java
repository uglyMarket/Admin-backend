//package com.sparta.uglymarket.dto;
//
//import com.sparta.uglymarket.entity.ProductEntity;
//import org.junit.jupiter.api.Test;
//
//import java.lang.reflect.Field;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//public class ProductsGetResponseTest {
//
//    @Test
//    void testDefaultConstructorAndGetters() {
//        // given
//        ProductsGetResponse response = new ProductsGetResponse();
//
//        // then
//        assertNull(response.getId());
//        assertNull(response.getTitle());
//        assertNull(response.getContent());
//        assertNull(response.getPrice());
//        assertNull(response.getStock());
//        assertNull(response.getImageUrl());
//        assertNull(response.getCategory());
//    }
//
//    @Test
//    void testParameterizedConstructorAndGetters() throws NoSuchFieldException, IllegalAccessException {
//        // given
//        ProductEntity product = new ProductEntity();
//        setFieldValue(product, "id", 1L);
//        setFieldValue(product, "title", "Product Title");
//        setFieldValue(product, "content", "Product Content");
//        setFieldValue(product, "price", 1000L);
//        setFieldValue(product, "stock", 10L);
//        setFieldValue(product, "imageUrl", "http://example.com/image.jpg");
//        setFieldValue(product, "category", "Category");
//
//        // when
//        ProductsGetResponse response = new ProductsGetResponse(product);
//
//        // then
//        assertNotNull(response);
//        assertEquals(1L, response.getId());
//        assertEquals("Product Title", response.getTitle());
//        assertEquals("Product Content", response.getContent());
//        assertEquals(1000L, response.getPrice());
//        assertEquals(10L, response.getStock());
//        assertEquals("http://example.com/image.jpg", response.getImageUrl());
//        assertEquals("Category", response.getCategory());
//    }
//
//    private void setFieldValue(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
//        Field field = object.getClass().getDeclaredField(fieldName);
//        field.setAccessible(true);
//        field.set(object, value);
//    }
//}
