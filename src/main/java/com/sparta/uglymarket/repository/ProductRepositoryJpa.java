package com.sparta.uglymarket.repository;

import com.sparta.uglymarket.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepositoryJpa extends JpaRepository<ProductEntity, Long>, ProductRepository {
}
