package com.sparta.uglymarket.controller;

import com.sparta.uglymarket.dto.*;
import com.sparta.uglymarket.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct() {
        // given
        ProductCreateRequest request = mock(ProductCreateRequest.class);
        ProductCreateResponse response = mock(ProductCreateResponse.class);
        when(productService.createProduct(any(ProductCreateRequest.class))).thenReturn(response);

        // when
        ResponseEntity<ProductCreateResponse> result = productController.createProduct(request);

        // then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(productService, times(1)).createProduct(request);
    }

    @Test
    void updateProduct() {
        // given
        Long id = 1L;
        ProductUpdateRequest request = mock(ProductUpdateRequest.class);
        ProductUpdateResponse response = mock(ProductUpdateResponse.class);
        when(productService.updateProduct(eq(id), any(ProductUpdateRequest.class))).thenReturn(response);

        // when
        ResponseEntity<ProductUpdateResponse> result = productController.updateProduct(id, request);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(productService, times(1)).updateProduct(eq(id), eq(request));
    }

    @Test
    void getProducts() {
        // given
        List<ProductsGetResponse> response = Collections.singletonList(mock(ProductsGetResponse.class));
        when(productService.getProducts()).thenReturn(response);

        // when
        ResponseEntity<List<ProductsGetResponse>> result = productController.getProducts();

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(productService, times(1)).getProducts();
    }

    @Test
    void getProduct() {
        // given
        Long id = 1L;
        ProductGetResponse response = mock(ProductGetResponse.class);
        when(productService.getProductResponse(eq(id))).thenReturn(response);

        // when
        ResponseEntity<ProductGetResponse> result = productController.getProduct(id);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(productService, times(1)).getProductResponse(eq(id));
    }

    @Test
    void deleteProduct() {
        // given
        Long id = 1L;
        ProductDeleteResponse response = mock(ProductDeleteResponse.class);
        when(productService.deleteProduct(eq(id))).thenReturn(response);

        // when
        ResponseEntity<ProductDeleteResponse> result = productController.deleteProduct(id);

        // then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(productService, times(1)).deleteProduct(eq(id));
    }
}
