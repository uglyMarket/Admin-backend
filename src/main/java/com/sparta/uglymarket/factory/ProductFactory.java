package com.sparta.uglymarket.factory;

import com.sparta.uglymarket.dto.ProductCreateRequest;
import com.sparta.uglymarket.entity.ProductEntity;

public interface ProductFactory {
    ProductEntity createProduct(ProductCreateRequest productCreateRequest);
}
