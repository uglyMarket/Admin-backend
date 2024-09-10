package com.sparta.uglymarket.domain;

public class ProductDomain {
    private Long id;
    private String title;
    private String content;
    private Long price;
    private Long stock;
    private String imageUrl;
    private String category;

    public ProductDomain(Long id, String title, String content, Long price, Long stock, String imageUrl, String category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    // Getter methods
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getPrice() {
        return price;
    }

    public Long getStock() {
        return stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }
}
