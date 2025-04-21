package oort.cloud.openmarket.products.entity;

import jakarta.persistence.*;
import oort.cloud.openmarket.category.entity.Category;

import java.util.Objects;

@Entity
@Table(name = "product_categories")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    protected ProductCategory() {}

    public static ProductCategory of(Products product, Category category) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.product = product;
        productCategory.category = category;
        return productCategory;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "productCategoryId=" + productCategoryId +
                ", product=" + product +
                ", category=" + category +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategory that = (ProductCategory) o;
        return Objects.equals(productCategoryId, that.productCategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCategoryId);
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public Products getProduct() {
        return product;
    }

    public Category getCategory() {
        return category;
    }
}
