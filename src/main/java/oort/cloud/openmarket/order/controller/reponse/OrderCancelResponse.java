package oort.cloud.openmarket.order.controller.reponse;

public class OrderCancelResponse {
    private Long orderId;

    public OrderCancelResponse(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
}
