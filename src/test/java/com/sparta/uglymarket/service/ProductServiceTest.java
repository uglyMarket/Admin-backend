package com.sparta.uglymarket.service;

import com.sparta.uglymarket.dto.*;
import com.sparta.uglymarket.entity.ProductEntity;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct() {
        // given
        ProductCreateRequest request = mock(ProductCreateRequest.class);
        ProductEntity productEntity = mock(ProductEntity.class);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        // when
        ProductCreateResponse response = productService.createProduct(request);

        // then
        assertEquals("상품이 성공적으로 생성되었습니다.", response.getMessage());
        assertEquals(productEntity, response.getProduct());
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }
}
