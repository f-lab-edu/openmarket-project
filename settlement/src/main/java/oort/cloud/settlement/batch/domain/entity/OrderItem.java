package oort.cloud.settlement.batch.domain.entity;

import jakarta.persistence.*;
import oort.cloud.settlement.batch.domain.enums.OrderItemStatus;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private int price;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderItemStatus status;

    @Column(name = "commission_rate")
    private Integer commissionRate;

    @Column(name = "commission_amount")
    private Integer commissionAmount;

    @Column(name = "delivered_at")
    private LocalDate deliveredAt;

    @Column(name = "confirmed_at")
    private LocalDate confirmedAt;

    protected OrderItem(){}

    private OrderItem(Builder builder) {
        this.orderId = builder.orderId;
        this.productId = builder.productId;
        this.userId = builder.userId;
        this.quantity = builder.quantity;
        this.price = builder.price;
        this.totalPrice = builder.totalPrice;
        this.status = builder.status;
        this.commissionRate = builder.commissionRate;
        this.commissionAmount = builder.commissionAmount;
        this.confirmedAt = builder.confirmedAt;
        this.deliveredAt = builder.deliveredAt;
    }
    public static class Builder{
        private Long orderId;
        private Long productId;
        private Long userId;
        private int quantity;
        private int price;
        private int totalPrice;
        private OrderItemStatus status;
        private Integer commissionRate;
        private Integer commissionAmount;

        private LocalDate deliveredAt;
        private LocalDate confirmedAt;

        public Builder(Long orderId, Long productId, Long userId){
            this.orderId = orderId;
            this.productId = productId;
            this.userId = userId;
        }

        public Builder quantity(int quantity){
            this.quantity = quantity;
            return this;
        }

        public Builder price(int price){
            this.price = price;
            return this;
        }

        public Builder totalPrice(int totalPrice){
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder orderItemStatus(OrderItemStatus status){
            this.status = status;
            return this;
        }

        public Builder commissionRate(int commissionRate){
            this.commissionRate = commissionRate;
            return this;
        }

        public Builder deliveredAt(LocalDate deliveredAt){
            this.deliveredAt = deliveredAt;
            return this;
        }
        public Builder commissionAmount(int commissionAmount){
            this.commissionAmount = commissionAmount;
            return this;
        }

        public Builder confirmedAt(LocalDate confirmedAt){
            this.confirmedAt = confirmedAt;
            return this;
        }

        public OrderItem build(){
            return new OrderItem(this);
        }
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public Long getUserId() {
        return userId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public OrderItemStatus getStatus() {
        return status;
    }

    public Integer getCommissionRate() {
        return commissionRate;
    }

    public Integer getCommissionAmount() {
        return commissionAmount;
    }

    public LocalDate getConfirmedAt() {
        return confirmedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(orderItemId, orderItem.orderItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItemId);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemId=" + orderItemId +
                ", userId=" + userId +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", commissionRate=" + commissionRate +
                ", commissionAmount=" + commissionAmount +
                ", confirmedAt=" + confirmedAt +
                '}';
    }
}
