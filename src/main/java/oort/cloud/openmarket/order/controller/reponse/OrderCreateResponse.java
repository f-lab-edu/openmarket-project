package oort.cloud.openmarket.order.controller.reponse;

public class OrderCreateResponse {
    private Long orderId;
    private String externalOrderId;

    public OrderCreateResponse(Long orderId, String externalOrderId) {
        this.orderId = orderId;
        this.externalOrderId = externalOrderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getExternalOrderId() {
        return externalOrderId;
    }

    @Override
    public String toString() {
        return "OrderCreateResponse{" +
                "orderId=" + orderId +
                ", externalOrderId='" + externalOrderId + '\'' +
                '}';
    }
}
