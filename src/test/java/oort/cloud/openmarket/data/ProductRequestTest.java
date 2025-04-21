package oort.cloud.openmarket.data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import oort.cloud.openmarket.products.controller.request.ProductRequest;

import java.util.List;

public class ProductRequestTest extends ProductRequest{
    private String productName;
    private String description;
    private int price;
    private int stock;
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

    public ProductRequestTest(String productName, String description, int price, int stock, Long categoryId) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
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
