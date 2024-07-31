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

    @Test
    void updateProduct() {
        // given
        Long id = 1L;
        ProductUpdateRequest request = mock(ProductUpdateRequest.class);
        ProductEntity productEntity = mock(ProductEntity.class);
        when(productRepository.findById(eq(id))).thenReturn(Optional.of(productEntity));

        doNothing().when(productEntity).updateFromRequest(any(ProductUpdateRequest.class));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        // when
        ProductUpdateResponse response = productService.updateProduct(id, request);

        // then
        assertEquals("상품이 성공적으로 업데이트되었습니다.", response.getMessage());
        assertEquals(productEntity, response.getProduct());
        verify(productRepository, times(1)).save(productEntity);
    }


    @Test
    void updateProduct_throwsException_whenProductNotFound() {
        // given
        Long id = 1L;
        ProductUpdateRequest request = mock(ProductUpdateRequest.class);
        when(productRepository.findById(eq(id))).thenReturn(Optional.empty());

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> productService.updateProduct(id, request));
        assertEquals(ErrorMsg.PRODUCT_NOT_FOUND.getHttpStatus(), exception.getHttpStatus());
    }
    @Test
    void getProducts() {
        // given
        List<ProductEntity> products = new ArrayList<>();
        products.add(mock(ProductEntity.class));
        when(productRepository.findAll()).thenReturn(products);

        // when
        List<ProductsGetResponse> response = productService.getProducts();

        // then
        assertFalse(response.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProducts_throwsException_whenNoProductsFound() {
        // given
        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> productService.getProducts());
        assertEquals(ErrorMsg.PRODUCT_NOT_FOUND.getHttpStatus(), exception.getHttpStatus());
    }
    @Test
    void getProductResponse() {
        // given
        Long id = 1L;
        ProductEntity productEntity = mock(ProductEntity.class);
        when(productRepository.findById(eq(id))).thenReturn(Optional.of(productEntity));

        // when
        ProductGetResponse response = productService.getProductResponse(id);

        // then
        assertEquals("상품 조회 성공", response.getMessage());
        assertEquals(productEntity, response.getProduct());
    }

    @Test
    void getProductResponse_throwsException_whenProductNotFound() {
        // given
        Long id = 1L;
        when(productRepository.findById(eq(id))).thenReturn(Optional.empty());

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> productService.getProductResponse(id));
        assertEquals(ErrorMsg.PRODUCT_NOT_FOUND.getHttpStatus(), exception.getHttpStatus());
    }

    @Test
    void deleteProduct() {
        // given
        Long id = 1L;

        // when
        ProductDeleteResponse response = productService.deleteProduct(id);

        // then
        assertEquals("상품이 성공적으로 삭제되었습니다.", response.getMessage());
        verify(productRepository, times(1)).deleteById(eq(id));
    }
}
