package oort.cloud.openmarket.order.controller.reponse;

public class OrderCreateResponse {
    private Long orderId;

    public OrderCreateResponse(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "OrderCreateResponse{" +
                "orderId=" + orderId +
                '}';
    }
}
