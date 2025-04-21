package oort.cloud.openmarket.products.entity;

import jakarta.persistence.*;
import oort.cloud.openmarket.common.entity.BaseTimeEntity;
import oort.cloud.openmarket.products.enums.ProductsEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Products extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private Long userId;
    private String productName;
    @Column(columnDefinition = "TEXT")
    private String description;
    private int price;
    private int stock;
    @Enumerated(EnumType.STRING)
    private ProductsEnum status;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductCategory> productCategories = new ArrayList<>();

    protected Products(){}

    public static Products create(String productName, Long userId, String description, int price, int stock){
        Products products = new Products();
        products.productName = productName;
        products.userId = userId;
        products.description = description;
        products.price = price;
        products.stock = stock;
        products.status = ProductsEnum.READY;
        return products;
    }

    public void addProductCategories(ProductCategory productCategory){
        productCategories.add(productCategory);
        productCategory.setProduct(this);
    }

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Products products = (Products) o;
        return Objects.equals(productId, products.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setStatus(ProductsEnum status) {
        this.status = status;
    }


    public Long getProductId() {
        return productId;
    }

    public Long getUserId(){ return userId; }

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

    public ProductsEnum getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Products{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", status=" + status +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                '}';
    }
}
