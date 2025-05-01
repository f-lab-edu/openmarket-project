package oort.cloud.openmarket.products.controller.response;

import jakarta.persistence.*;
import oort.cloud.openmarket.products.entity.ProductCategory;
import oort.cloud.openmarket.products.entity.Products;
import oort.cloud.openmarket.products.enums.ProductsStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ProductDetailResponse {
    private Long productId;
    private String productName;
    private String description;
    private int price;
    private int stock;
    private ProductsStatus status;
    private Long sellerId;
    private String sellerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Long> categoryIds;

    public ProductDetailResponse(Products product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.status = product.getStatus();
        this.sellerId = product.getUser().getUserId();
        this.sellerName = product.getUser().getUserName();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
        this.categoryIds = product.getProductCategories()
                .stream()
                .map(pc -> pc.getCategory().getCategoryId())
                .toList();
    }

    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getDescription() { return description; }
    public int getPrice() { return price; }
    public int getStock() { return stock; }
    public ProductsStatus getStatus() { return status; }
    public Long getSellerId() { return sellerId; }
    public String getSellerName() { return sellerName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<Long> getCategoryIds() { return categoryIds; }
}
