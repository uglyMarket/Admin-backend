package com.sparta.uglymarket.controller;

import com.sparta.uglymarket.domain.ProductDomain;
import com.sparta.uglymarket.dto.*;
import com.sparta.uglymarket.mapper.ProductMapper;
import com.sparta.uglymarket.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    // 상품 생성
    @PostMapping
    public ResponseEntity<ProductCreateResponse> createProduct(@Valid @RequestBody ProductCreateRequest productCreateRequest) {
        ProductDomain productDomain = productMapper.dtoToDomain(productCreateRequest);
        ProductCreateResponse productCreateResponse = productService.createProduct(productDomain);
        return new ResponseEntity<>(productCreateResponse, HttpStatus.CREATED);
    }

    // 상품 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<ProductUpdateResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest productUpdateRequest) {
        ProductDomain productDomain = productMapper.dtoToDomain(productUpdateRequest);
        ProductUpdateResponse productUpdateResponse = productService.updateProduct(id, productDomain);
        return new ResponseEntity<>(productUpdateResponse, HttpStatus.OK);
    }

    // 모든 상품 조회
    @GetMapping
    public ResponseEntity<List<ProductsGetResponse>> getProducts() {
        List<ProductsGetResponse> productsGetResponse = productService.getProducts();
        return new ResponseEntity<>(productsGetResponse, HttpStatus.OK);
    }

    // 특정 상품 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductGetResponse> getProduct(@PathVariable Long id) {
        ProductGetResponse productGetResponse = productService.getProduct(id);
        return new ResponseEntity<>(productGetResponse, HttpStatus.OK);
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDeleteResponse> deleteProduct(@PathVariable Long id) {
        ProductDeleteResponse productDeleteResponse = productService.deleteProduct(id);
        return new ResponseEntity<>(productDeleteResponse, HttpStatus.OK);
    }
}
