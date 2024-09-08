package com.sparta.uglymarket.mapper;

import com.sparta.uglymarket.dto.ProductCreateRequest;
import com.sparta.uglymarket.dto.ProductGetResponse;
import com.sparta.uglymarket.dto.ProductsGetResponse;
import com.sparta.uglymarket.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDtoMapper {

//    public ProductEntity toEntity(ProductCreateRequest request) {
//        return new ProductEntity(
//                null,
//                request.getTitle(),
//                request.getContent(),
//                request.getPrice(),
//                request.getStock(),
//                request.getImageUrl(),
//                request.getCategory()
//        );
//    }

    public ProductGetResponse toProductGetResponse(ProductEntity product) {
        return new ProductGetResponse("상품 조회 성공", product);
    }

    public List<ProductsGetResponse> toProductsGetResponseList(List<ProductEntity> products) {
        return products.stream()
                .map(ProductsGetResponse::new)
                .collect(Collectors.toList());
    }
}
