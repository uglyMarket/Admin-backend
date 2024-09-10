package com.sparta.uglymarket.mapper;

import com.sparta.uglymarket.domain.ProductDomain;
import com.sparta.uglymarket.dto.*;
import com.sparta.uglymarket.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    // ProductCreateRequest -> Domain 변환
    public ProductDomain dtoToDomain(ProductCreateRequest dto) {
        return new ProductDomain(
                null,
                dto.getTitle(),
                dto.getContent(),
                dto.getPrice(),
                dto.getStock(),
                dto.getImageUrl(),
                dto.getCategory()
        );
    }

    // ProductUpdateRequest -> Domain 변환
    public ProductDomain dtoToDomain(ProductUpdateRequest dto) {
        return new ProductDomain(
                null,
                dto.getTitle(),
                dto.getContent(),
                dto.getPrice(),
                dto.getStock(),
                dto.getImageUrl(),
                dto.getCategory()
        );
    }

    // Domain -> Entity 변환
    public ProductEntity domainToEntity(ProductDomain domain) {
        return new ProductEntity(
                domain.getId(),
                domain.getTitle(),
                domain.getContent(),
                domain.getPrice(),
                domain.getStock(),
                domain.getImageUrl(),
                domain.getCategory()
        );
    }

    // 기존 ProductEntity의 필드를 ProductDomain의 값으로 업데이트
    public ProductEntity updateEntityFromDomain(ProductDomain productDomain, ProductEntity productEntity) {
        productEntity.changeTitle(productDomain.getTitle())
                .changeContent(productDomain.getContent())
                .changePrice(productDomain.getPrice())
                .changeStock(productDomain.getStock())
                .changeImageUrl(productDomain.getImageUrl())
                .changeCategory(productDomain.getCategory());
        return productEntity;
    }

    // ProductEntity -> ProductDomain 변환
    public ProductDomain entityToDomain(ProductEntity productEntity) {
        return new ProductDomain(
                productEntity.getId(),
                productEntity.getTitle(),
                productEntity.getContent(),
                productEntity.getPrice(),
                productEntity.getStock(),
                productEntity.getImageUrl(),
                productEntity.getCategory()
        );
    }



    // Domain -> Response DTO 변환
    public ProductCreateResponse domainToCreateResponse(ProductDomain domain) {
        return new ProductCreateResponse("상품이 성공적으로 생성되었습니다.", domain);
    }

    public ProductUpdateResponse domainToUpdateResponse(ProductDomain domain) {
        return new ProductUpdateResponse("상품이 성공적으로 업데이트되었습니다.", domain);
    }

    // ProductEntity 리스트 -> Domain 리스트 변환
    public List<ProductDomain> entityListToDomainList(List<ProductEntity> entities) {
        return entities.stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    // Domain 리스트 -> DTO 변환
    public List<ProductsGetResponse> domainListToResponseList(List<ProductDomain> domains) {
        return domains.stream()
                .map(domain -> new ProductsGetResponse(
                        domain.getId(),
                        domain.getTitle(),
                        domain.getContent(),
                        domain.getPrice(),
                        domain.getStock(),
                        domain.getImageUrl(),
                        domain.getCategory()
                ))
                .collect(Collectors.toList());
    }
}
