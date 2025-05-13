package oort.cloud.openmarket.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import oort.cloud.openmarket.order.controller.request.OrderCreateRequest;
import oort.cloud.openmarket.order.controller.request.OrderItemCreateRequest;

import java.util.List;

public class OrderCreateRequestTest extends OrderCreateRequest {
    @NotEmpty
    private List<OrderItemCreateRequest> orderItemRequests;

    @NotNull
    private Long addressId;

    @NotBlank
    private String receiverName;

    @NotBlank
    private String receiverPhone;

    public OrderCreateRequestTest(List<OrderItemCreateRequest> orderItemRequests, Long addressId, String receiverName, String receiverPhone) {
        this.orderItemRequests = orderItemRequests;
        this.addressId = addressId;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
    }

    @Override
    public List<OrderItemCreateRequest> getOrderItemRequests() {
        return this.orderItemRequests;
    }

    @Override
    public Long getAddressId() {
        return this.addressId;
    }

    @Override
    public String getReceiverName() {
        return this.receiverName;
    }

    @Override
    public String getReceiverPhone() {
        return this.receiverPhone;
    }
}
