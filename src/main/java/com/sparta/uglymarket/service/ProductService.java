package com.sparta.uglymarket.service;

import com.sparta.uglymarket.domain.ProductDomain;
import com.sparta.uglymarket.dto.*;
import com.sparta.uglymarket.entity.ProductEntity;
import com.sparta.uglymarket.mapper.ProductMapper;
import com.sparta.uglymarket.repository.ProductRepository;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    // 상품 생성
    @Transactional
    public ProductCreateResponse createProduct(ProductDomain productDomain) {
        ProductEntity productEntity = productMapper.domainToEntity(productDomain);
        ProductEntity savedProduct = productRepository.save(productEntity);
        return productMapper.domainToCreateResponse(productMapper.entityToDomain(savedProduct));
    }

    @Transactional
    public ProductUpdateResponse updateProduct(Long id, ProductDomain productDomain) {
        // 기존 상품을 데이터베이스에서 조회
        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.PRODUCT_NOT_FOUND));

        // Mapper를 통해 ProductDomain의 변경 사항을 ProductEntity에 반영
        productMapper.updateEntityFromDomain(productDomain, existingProduct);

        // 수정된 엔티티를 저장
        ProductEntity updatedProduct = productRepository.save(existingProduct);

        // 저장된 엔티티를 Domain으로 변환하여 응답 생성
        return productMapper.domainToUpdateResponse(productMapper.entityToDomain(updatedProduct));
    }


    // 모든 상품 조회
    public List<ProductsGetResponse> getProducts() {
        List<ProductEntity> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new CustomException(ErrorMsg.PRODUCT_NOT_FOUND);
        }

        return productMapper.domainListToResponseList(
                productMapper.entityListToDomainList(products)
        );
    }

    // 특정 상품 조회
    public ProductGetResponse getProduct(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.PRODUCT_NOT_FOUND));

        ProductDomain productDomain = productMapper.entityToDomain(product);
        return new ProductGetResponse(
                productDomain.getId(),
                productDomain.getTitle(),
                productDomain.getContent(),
                productDomain.getPrice(),
                productDomain.getStock(),
                productDomain.getImageUrl(),
                productDomain.getCategory()
        );
    }

    // 상품 삭제
    @Transactional
    public ProductDeleteResponse deleteProduct(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.PRODUCT_NOT_FOUND));

        productRepository.delete(product);
        return new ProductDeleteResponse("상품이 성공적으로 삭제되었습니다.");
    }
}
