package oort.cloud.openmarket.shipment.controller.response;

import oort.cloud.openmarket.shipment.enums.ShipmentStatus;

import java.time.LocalDateTime;

public class ShipmentResponse {
    private Long shipmentId;
    private Long sellerId;
    private Long orderItemId;
    private String trackingNumber;
    private String deliveryCompany;
    private ShipmentStatus status;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;

    public ShipmentResponse(Long shipmentId, Long sellerId, Long orderItemId, String trackingNumber, String deliveryCompany, ShipmentStatus status, LocalDateTime shippedAt, LocalDateTime deliveredAt) {
        this.shipmentId = shipmentId;
        this.sellerId = sellerId;
        this.orderItemId = orderItemId;
        this.trackingNumber = trackingNumber;
        this.deliveryCompany = deliveryCompany;
        this.status = status;
        this.shippedAt = shippedAt;
        this.deliveredAt = deliveredAt;
    }

    @Override
    public String toString() {
        return "ShipmentResponse{" +
                "shipmentId=" + shipmentId +
                ", sellerId=" + sellerId +
                ", orderItemId=" + orderItemId +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", deliveryCompany='" + deliveryCompany + '\'' +
                ", status=" + status +
                ", shippedAt=" + shippedAt +
                ", deliveredAt=" + deliveredAt +
                '}';
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getShippedAt() {
        return shippedAt;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }
}
