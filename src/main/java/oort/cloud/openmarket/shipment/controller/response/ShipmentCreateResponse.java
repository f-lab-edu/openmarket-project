package oort.cloud.openmarket.shipment.controller.response;

public class ShipmentCreateResponse {
    private Long shipmentId;

    public ShipmentCreateResponse(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Override
    public String toString() {
        return "ShipmentCreateResponse{" +
                "shipmentId=" + shipmentId +
                '}';
    }

    public Long getShipmentId() {
        return shipmentId;
    }
}
