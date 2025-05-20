package oort.cloud.openmarket.shipment.entity;

import jakarta.persistence.*;
import oort.cloud.openmarket.order.entity.OrderItem;
import oort.cloud.openmarket.shipment.enums.ShipmentStatus;
import oort.cloud.openmarket.user.entity.Users;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "shipment")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToOne
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "delivery_company")
    private String deliveryCompany;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    @Column(name = "shipped_at")
    private LocalDateTime shippedAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    protected Shipment(){}

    public static Shipment createShipment(
            Users seller,
            String trackingNumber,
            String deliveryCompany,
            OrderItem orderItem){
        Shipment shipment = new Shipment();
        shipment.user = seller;
        shipment.trackingNumber = trackingNumber;
        shipment.deliveryCompany = deliveryCompany;
        shipment.status = ShipmentStatus.SHIPPING;
        shipment.shippedAt = LocalDateTime.now();
        shipment.orderItem = orderItem;
        return shipment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipment shipment = (Shipment) o;
        return shipmentId.equals(shipment.shipmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipmentId);
    }

    @Override
    public String toString() {
        return "Shipment{" +
                "shipmentId=" + shipmentId +
                ", user=" + user +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", deliveryCompany='" + deliveryCompany + '\'' +
                ", status=" + status +
                ", shippedAt=" + shippedAt +
                ", deliveredAt=" + deliveredAt +
                '}';
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public Users getUser() {
        return user;
    }

    public String getTrackingNumber() {
        return trackingNumber;
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

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }
}
