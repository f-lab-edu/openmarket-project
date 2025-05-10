package oort.cloud.openmarket.category.controller.reponse;

public class CreateCategoryResponse {
    private Long categoryId;

    public CreateCategoryResponse(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
