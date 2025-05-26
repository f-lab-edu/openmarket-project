package oort.cloud.openmarket.shipment.controller.request;

import oort.cloud.openmarket.shipment.enums.ShipmentStatus;

import java.time.LocalDate;

public class ShipmentSearchRequest {
    private ShipmentStatus status;
    private LocalDate startDay;
    private LocalDate endDay;

    public ShipmentStatus getStatus() {
        return status;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public LocalDate getEndDay() {
        return endDay;
    }
}
