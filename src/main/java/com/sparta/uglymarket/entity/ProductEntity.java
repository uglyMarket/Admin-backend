package com.sparta.uglymarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long stock;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String category;

    // 필드 업데이트 메서드들 (Setter 대신 명시적 메서드)
    public ProductEntity changeTitle(String title) {
        this.title = title;
        return this;
    }

    public ProductEntity changeContent(String content) {
        this.content = content;
        return this;
    }

    public ProductEntity changePrice(Long price) {
        this.price = price;
        return this;
    }

    public ProductEntity changeStock(Long stock) {
        this.stock = stock;
        return this;
    }

    public ProductEntity changeImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ProductEntity changeCategory(String category) {
        this.category = category;
        return this;
    }
}
