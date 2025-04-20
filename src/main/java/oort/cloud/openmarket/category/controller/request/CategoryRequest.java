package oort.cloud.openmarket.category.controller.request;

import jakarta.validation.constraints.NotEmpty;

public class CategoryRequest {
    @NotEmpty
    private String categoryName;
    private Long parentId;

    public String getCategoryName() {
        return categoryName;
    }
    public Long getParentId() {
        return parentId;
    }
}
