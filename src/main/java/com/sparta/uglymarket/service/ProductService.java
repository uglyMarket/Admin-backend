package com.sparta.uglymarket.service;

import com.sparta.uglymarket.dto.*;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.entity.ProductEntity;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 생성
    public ProductCreateResponse createProduct(ProductCreateRequest productCreateRequest) {
        ProductEntity productEntity = new ProductEntity(productCreateRequest);
        ProductEntity savedProduct = productRepository.save(productEntity);
        return new ProductCreateResponse("상품이 성공적으로 생성되었습니다.", savedProduct);
    }

    // 상품 업데이트
    @Transactional
    public ProductUpdateResponse updateProduct(Long id, ProductUpdateRequest productUpdateRequest) {
        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.PRODUCT_NOT_FOUND));

        existingProduct.updateFromRequest(productUpdateRequest);
        ProductEntity updatedProduct = productRepository.save(existingProduct);
        return new ProductUpdateResponse("상품이 성공적으로 업데이트되었습니다.", updatedProduct);
    }

    // 모든 상품 조회
    public List<ProductsGetResponse> getProducts() {
        List<ProductEntity> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new CustomException(ErrorMsg.PRODUCT_NOT_FOUND);
        }

        List<ProductsGetResponse> productsGetResponseList = new ArrayList<>();
        for (ProductEntity product : products) {
            productsGetResponseList.add(new ProductsGetResponse(product));
        }

        return productsGetResponseList;
    }

    // 특정 상품 조회
    public ProductGetResponse getProductResponse(Long id) {
        return productRepository.findById(id)
                .map(product -> new ProductGetResponse("상품 조회 성공", product))
                .orElseThrow(() -> new CustomException(ErrorMsg.PRODUCT_NOT_FOUND));
    }


    // 상품 삭제
    public ProductDeleteResponse deleteProduct(Long id) {
        productRepository.deleteById(id);
        return new ProductDeleteResponse("상품이 성공적으로 삭제되었습니다.");
    }

}
