//package com.sparta.uglymarket.entity;
//
//import com.sparta.uglymarket.dto.ProductCreateRequest;
//import com.sparta.uglymarket.dto.ProductUpdateRequest;
//import com.sparta.uglymarket.factory.ProductFactoryq;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//public class ProductEntityTest {
//
//    @Mock
//    private ProductCreateRequest productCreateRequest;
//
//    @Mock
//    private ProductUpdateRequest productUpdateRequest;
//
//    @InjectMocks
//    private ProductFactoryq productFactory;
//
//    private ProductEntity productEntity;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // 설정: ProductCreateRequest 목 객체 생성 및 값 설정
//        when(productCreateRequest.getTitle()).thenReturn("Sample Title");
//        when(productCreateRequest.getContent()).thenReturn("Sample Content");
//        when(productCreateRequest.getPrice()).thenReturn(1000L);
//        when(productCreateRequest.getStock()).thenReturn(50L);
//        when(productCreateRequest.getImageUrl()).thenReturn("http://example.com/image.jpg");
//        when(productCreateRequest.getCategory()).thenReturn("Sample Category");
//
//        // ProductFactory를 사용하여 ProductEntity 객체 생성
//        productEntity = productFactory.createProduct(productCreateRequest);
//
//        // 설정: ProductUpdateRequest 목 객체 생성 및 값 설정
//        when(productUpdateRequest.getTitle()).thenReturn("Updated Title");
//        when(productUpdateRequest.getContent()).thenReturn("Updated Content");
//        when(productUpdateRequest.getPrice()).thenReturn(2000L);
//        when(productUpdateRequest.getStock()).thenReturn(30L);
//        when(productUpdateRequest.getImageUrl()).thenReturn("http://example.com/newimage.jpg");
//        when(productUpdateRequest.getCategory()).thenReturn("Updated Category");
//    }
//
//    @Test
//    void testProductEntityConstructor() {
//        // then
//        assertEquals(productCreateRequest.getTitle(), productEntity.getTitle());
//        assertEquals(productCreateRequest.getContent(), productEntity.getContent());
//        assertEquals(productCreateRequest.getPrice(), productEntity.getPrice());
//        assertEquals(productCreateRequest.getStock(), productEntity.getStock());
//        assertEquals(productCreateRequest.getImageUrl(), productEntity.getImageUrl());
//        assertEquals(productCreateRequest.getCategory(), productEntity.getCategory());
//    }
//
//    @Test
//    void testUpdateFromRequest() {
//        // when
//        productEntity.updateFromRequest(productUpdateRequest);
//
//        // then
//        assertEquals(productUpdateRequest.getTitle(), productEntity.getTitle());
//        assertEquals(productUpdateRequest.getContent(), productEntity.getContent());
//        assertEquals(productUpdateRequest.getPrice(), productEntity.getPrice());
//        assertEquals(productUpdateRequest.getStock(), productEntity.getStock());
//        assertEquals(productUpdateRequest.getImageUrl(), productEntity.getImageUrl());
//        assertEquals(productUpdateRequest.getCategory(), productEntity.getCategory());
//    }
//}
