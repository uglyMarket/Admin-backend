package com.sparta.uglymarket.service;

import com.sparta.uglymarket.dto.ProductGetResponse;
import com.sparta.uglymarket.dto.ProductsGetResponse;
import com.sparta.uglymarket.entity.ProductEntity;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.mapper.ProductDtoMapper;
import com.sparta.uglymarket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductGetService {

    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;

    public List<ProductsGetResponse> getProducts() {
        List<ProductEntity> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new CustomException(ErrorMsg.PRODUCT_NOT_FOUND);
        }
        return productDtoMapper.toProductsGetResponseList(products);
    }

    public ProductGetResponse getProductResponse(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.PRODUCT_NOT_FOUND));
        return productDtoMapper.toProductGetResponse(product);
    }
}
