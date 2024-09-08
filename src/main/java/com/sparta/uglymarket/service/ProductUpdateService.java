package com.sparta.uglymarket.service;

import com.sparta.uglymarket.dto.ProductUpdateRequest;
import com.sparta.uglymarket.dto.ProductUpdateResponse;
import com.sparta.uglymarket.entity.ProductEntity;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductUpdateService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductUpdateResponse updateProduct(Long id, ProductUpdateRequest productUpdateRequest) {
        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.PRODUCT_NOT_FOUND));
        existingProduct.updateFromRequest(productUpdateRequest);
        ProductEntity updatedProduct = productRepository.save(existingProduct);
        return new ProductUpdateResponse("상품이 성공적으로 업데이트되었습니다.", updatedProduct);
    }
}
