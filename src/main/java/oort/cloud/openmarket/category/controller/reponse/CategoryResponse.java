package oort.cloud.openmarket.category.controller.reponse;

import com.querydsl.core.annotations.QueryProjection;

public class CategoryResponse {
    private Long categoryId;
    private String name;

    @QueryProjection
    public CategoryResponse(Long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                '}';
    }
}
