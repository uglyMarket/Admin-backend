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

    @Test
    void testUpdateProduct_ThrowsException() {
        // given
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> {
            productService.updateProduct(1L, productUpdateRequest);
        });

        assertEquals(ErrorMsg.PRODUCT_NOT_FOUND.getDetails(), exception.getMessage());
    }
    @Test
    void testGetProducts() {
        // given
        when(productRepository.findAll()).thenReturn(List.of(productEntity));

        // when
        List<ProductsGetResponse> response = productService.getProducts();

        // then
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
    }
    @Test
    void testGetProducts_ThrowsException() {
        // given
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        // when & then
        CustomException exception = assertThrows(CustomException.class, productService::getProducts);

        assertEquals(ErrorMsg.PRODUCT_NOT_FOUND.getDetails(), exception.getMessage());
    }

    @Test
    void testGetProductResponse() {
        // given
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(productEntity));

        // when
        ProductGetResponse response = productService.getProductResponse(1L);

        // then
        assertEquals("상품 조회 성공", response.getMessage());
        assertEquals(productEntity, response.getProduct());
    }

    @Test
    void testGetProductResponse_ThrowsException() {
        // given
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> {
            productService.getProductResponse(1L);
        });

        assertEquals(ErrorMsg.PRODUCT_NOT_FOUND.getDetails(), exception.getMessage());
    }
}
