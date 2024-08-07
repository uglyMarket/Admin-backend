package com.sparta.uglymarket.factory;

import com.sparta.uglymarket.dto.ProductCreateRequest;
import com.sparta.uglymarket.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory {

    public ProductEntity createProduct(ProductCreateRequest productCreateRequest) {
        return new ProductEntity(
                null,
                productCreateRequest.getTitle(),
                productCreateRequest.getContent(),
                productCreateRequest.getPrice(),
                productCreateRequest.getStock(),
                productCreateRequest.getImageUrl(),
                productCreateRequest.getCategory()
        );
    }
}
