package oort.cloud.openmarket.products.controller.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import oort.cloud.openmarket.products.enums.ProductsEnum;

import java.time.LocalDateTime;
import java.util.List;

public class ProductRequest {
    @NotEmpty(message = "상품명은 필수 값입니다.")
    private String productName;
    private String description;
    @Positive(message = "가격은 0이상 입니다.")
    private int price;
    @Positive(message = "재고는 0이상 입니다.")
    private int stock;
    @NotNull(message = "카테고리는 필수 값입니다.")
    private Long categoryId;

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    @Override
    public String toString() {
        return "ProductRequest{" +
                "productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", categoryIds=" + categoryId +
                '}';
    }
}
