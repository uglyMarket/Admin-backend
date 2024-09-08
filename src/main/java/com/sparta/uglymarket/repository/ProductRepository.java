package com.sparta.uglymarket.repository;

import com.sparta.uglymarket.entity.ProductEntity;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<ProductEntity> findById(Long id);
    List<ProductEntity> findAll();
    ProductEntity save(ProductEntity productEntity);
    void delete(ProductEntity productEntity);
}

