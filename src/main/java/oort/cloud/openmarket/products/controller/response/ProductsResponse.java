package oort.cloud.openmarket.products.controller.response;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;

public class ProductsResponse{
    private Long productId;
    private String productName;
    private int price;
    private int stock;
    private LocalDateTime salesAt;
    private Long sellerId;
    private String sellerName;

    @QueryProjection
    public ProductsResponse(Long productId, String productName, int price, int stock,
                            LocalDateTime salesAt, Long sellerId, String sellerName) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.salesAt = salesAt;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public LocalDateTime getSalesAt() {
        return salesAt;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    @Override
    public String toString() {
        return "ProductsResponse{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", salesAt=" + salesAt +
                ", sellerId=" + sellerId +
                ", sellerName='" + sellerName + '\'' +
                '}';
    }
}
