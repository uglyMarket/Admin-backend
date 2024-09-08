package com.sparta.uglymarket.service;

import com.sparta.uglymarket.dto.ProductDeleteResponse;
import com.sparta.uglymarket.entity.ProductEntity;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDeleteService {

    private final ProductRepository productRepository;

    public ProductDeleteResponse deleteProduct(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.PRODUCT_NOT_FOUND));
        productRepository.delete(product);
        return new ProductDeleteResponse("상품이 성공적으로 삭제되었습니다.");
    }
}
