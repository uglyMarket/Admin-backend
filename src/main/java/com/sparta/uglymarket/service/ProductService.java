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
        ProductEntity productEntity = convertToEntity(productCreateRequest);
        ProductEntity savedProduct = saveProduct(productEntity);
        return new ProductCreateResponse("상품이 성공적으로 생성되었습니다.", savedProduct);
    }

    // 상품 업데이트
    @Transactional
    public ProductUpdateResponse updateProduct(Long id, ProductUpdateRequest productUpdateRequest) {
        ProductEntity existingProduct = findProductById(id);
        updateProductEntity(existingProduct, productUpdateRequest);
        ProductEntity updatedProduct = saveProduct(existingProduct);
        return new ProductUpdateResponse("상품이 성공적으로 업데이트되었습니다.", updatedProduct);
    }

    // 모든 상품 조회
    public List<ProductsGetResponse> getProducts() {
        List<ProductEntity> products = fetchAllProducts();
        checkIfProductsEmpty(products);
        return convertToResponseList(products);
    }

    // 특정 상품 조회
    public ProductGetResponse getProductResponse(Long id) {
        ProductEntity product = findProductById(id);
        return convertToProductGetResponse(product);
    }

    // 상품 삭제
    public ProductDeleteResponse deleteProduct(Long id) {
        ProductEntity product = findProductById(id);
        deleteProductEntity(product);
        return new ProductDeleteResponse("상품이 성공적으로 삭제되었습니다.");
    }

    // DTO를 엔티티로 변환
    private ProductEntity convertToEntity(ProductCreateRequest productCreateRequest) {
        return new ProductEntity(productCreateRequest);
    }

    // 엔티티 저장
    private ProductEntity saveProduct(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    // ID로 제품 찾기
    private ProductEntity findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorMsg.PRODUCT_NOT_FOUND));
    }

    // 제품 엔티티 업데이트
    private void updateProductEntity(ProductEntity existingProduct, ProductUpdateRequest productUpdateRequest) {
        existingProduct.updateFromRequest(productUpdateRequest);
    }

    // 모든 제품을 가져오기
    private List<ProductEntity> fetchAllProducts() {
        return productRepository.findAll();
    }

    // 제품 목록이 비어 있는지 확인
    private void checkIfProductsEmpty(List<ProductEntity> products) {
        if (products.isEmpty()) {
            throw new CustomException(ErrorMsg.PRODUCT_NOT_FOUND);
        }
    }

    // 제품 엔티티 목록을 DTO 목록으로 변환
    private List<ProductsGetResponse> convertToResponseList(List<ProductEntity> products) {
        List<ProductsGetResponse> productsGetResponseList = new ArrayList<>();
        for (ProductEntity product : products) {
            productsGetResponseList.add(new ProductsGetResponse(product));
        }
        return productsGetResponseList;
    }

    // 제품 엔티티를 ProductGetResponse로 변환
    private ProductGetResponse convertToProductGetResponse(ProductEntity product) {
        return new ProductGetResponse("상품 조회 성공", product);
    }

    // 제품 엔티티 삭제
    private void deleteProductEntity(ProductEntity product) {
        productRepository.delete(product);
    }
}
