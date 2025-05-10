package oort.cloud.openmarket.category.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @BatchSize(size = 20) //N + 1 문제 해결 방법 1
    private List<Category> children = new ArrayList<>();

    protected Category() {}

    public static Category of(String categoryName, Category parent) {
        Category category = new Category();
        category.categoryName = categoryName;
        category.parent = parent;
        return category;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Category getParent() {
        return parent;
    }

    public List<Category> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", parent=" + parent +
                ", children=" + children +
                '}';
    }
}

