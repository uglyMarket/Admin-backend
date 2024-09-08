package com.sparta.uglymarket.service;

import com.sparta.uglymarket.dto.ProductCreateRequest;
import com.sparta.uglymarket.dto.ProductCreateResponse;
import com.sparta.uglymarket.entity.ProductEntity;
import com.sparta.uglymarket.factory.ProductFactory;
import com.sparta.uglymarket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCreateService {

    private final ProductRepository productRepository;
    private final ProductFactory productFactory;

    public ProductCreateResponse createProduct(ProductCreateRequest productCreateRequest) {
        ProductEntity productEntity = productFactory.createProduct(productCreateRequest);
        ProductEntity savedProduct = productRepository.save(productEntity);
        return new ProductCreateResponse("상품이 성공적으로 생성되었습니다.", savedProduct);
    }
}
