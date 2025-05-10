package oort.cloud.openmarket.data;

import jakarta.validation.constraints.NotEmpty;
import oort.cloud.openmarket.category.controller.request.CategoryRequest;

public class CategoryRequestTest extends CategoryRequest {
    @NotEmpty
    private String categoryName;
    private Long parentId;

    public CategoryRequestTest(String categoryName, Long parentId){
        this.categoryName = categoryName;
        this.parentId = parentId;
    }

    @Override
    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public Long getParentId() {
        return parentId;
    }

    @Override
    public String toString() {
        return "CategoryRequestTest{" +
                "categoryName='" + categoryName + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
