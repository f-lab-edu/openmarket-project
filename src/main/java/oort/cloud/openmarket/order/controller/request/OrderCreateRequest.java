package oort.cloud.openmarket.order.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderCreateRequest {
    @NotEmpty
    private List<OrderItemCreateRequest> orderItemRequests;

    @NotNull
    private Long addressId;

    @NotBlank
    private String receiverName;

    @NotBlank
    private String receiverPhone;

    public List<OrderItemCreateRequest> getOrderItemRequests() {
        return orderItemRequests;
    }

    public Long getAddressId() {
        return addressId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

}
