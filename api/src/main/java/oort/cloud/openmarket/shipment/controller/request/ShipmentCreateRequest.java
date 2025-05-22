package oort.cloud.openmarket.shipment.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ShipmentCreateRequest {
    @NotNull(message = "주문 상품 ID는 필수 값 입니다.")
    private Long orderItemId;
    @NotEmpty(message = "송장 번호는 필수 값 입니다.")
    private String trackingNumber;
    @NotEmpty(message = "배송 회사명은 필수 값 입니다.")
    private String deliveryCompany;

    public Long getOrderItemId() {
        return orderItemId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }
}
