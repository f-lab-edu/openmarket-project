package oort.cloud.openmarket.products.controller.response;

public class CreateProductResponse {
    private Long productId;

    public CreateProductResponse(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
