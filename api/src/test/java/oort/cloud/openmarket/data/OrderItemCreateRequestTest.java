package oort.cloud.openmarket.data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import oort.cloud.openmarket.order.controller.request.OrderItemCreateRequest;

public class OrderItemCreateRequestTest extends OrderItemCreateRequest {
    @NotNull
    private Long productId;

    @Min(1)
    private int quantity;

    public OrderItemCreateRequestTest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    @Override
    public Long getProductId() {
        return this.productId;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }
}
