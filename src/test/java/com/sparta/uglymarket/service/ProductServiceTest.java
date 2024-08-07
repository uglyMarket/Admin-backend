package com.sparta.uglymarket.service;

import com.sparta.uglymarket.dto.*;
import com.sparta.uglymarket.entity.ProductEntity;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.factory.ProductFactory;
import com.sparta.uglymarket.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductFactory productFactory;

    @InjectMocks
    private ProductService productService;

    private ProductEntity productEntity;
    private ProductCreateRequest productCreateRequest;
    private ProductUpdateRequest productUpdateRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productCreateRequest = mock(ProductCreateRequest.class);
        productUpdateRequest = mock(ProductUpdateRequest.class);

        productEntity = new ProductEntity(1L, "Sample Title", "Sample Content", 1000L, 50L, "http://example.com/image.jpg", "Sample Category");
    }

    @Test
    void testCreateProduct() {
        // given
        when(productFactory.createProduct(any(ProductCreateRequest.class))).thenReturn(productEntity);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        // when
        ProductCreateResponse response = productService.createProduct(productCreateRequest);

        // then
        assertEquals("상품이 성공적으로 생성되었습니다.", response.getMessage());
        assertEquals(productEntity, response.getProduct());
        verify(productRepository).save(productEntity);
    }
    @Test
    void testUpdateProduct() {
        // given
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productEntity));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        // when
        ProductUpdateResponse response = productService.updateProduct(1L, productUpdateRequest);

        // then
        assertEquals("상품이 성공적으로 업데이트되었습니다.", response.getMessage());
        assertEquals(productEntity, response.getProduct());
        verify(productRepository).save(productEntity);
    }
}
