package oort.cloud.openmarket.order.entity;

import jakarta.persistence.*;
import oort.cloud.openmarket.exception.business.UnsupportedStatusException;
import oort.cloud.openmarket.order.enums.OrderItemStatus;
import oort.cloud.openmarket.products.entity.Products;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Products product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private int price;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderItemStatus status;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    protected OrderItem(){}

    public static OrderItem createOrderItem(
            Products product,
            int quantity
    ){
        OrderItem orderItem = new OrderItem();
        orderItem.product = product;
        orderItem.quantity = quantity;
        orderItem.price = product.getPrice();
        orderItem.totalPrice = product.getPrice() * quantity;
        orderItem.status = OrderItemStatus.ORDERED;
        product.removeStock(quantity);
        return orderItem;
    }

    public void cancel(){
        if(!status.isCancellable()){
            throw new UnsupportedStatusException();
        }
        product.addStock(quantity);
        this.setStatus(OrderItemStatus.CANCELLED);
    }


    public void setOrder(Order order) {
        this.order = order;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStatus(OrderItemStatus status) {
        this.status = status;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public Order getOrder() {
        return order;
    }

    public Products getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public OrderItemStatus getStatus() {
        return status;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public LocalDateTime getConfirmedAt() {
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
                ", order=" + order +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", deliveredAt=" + deliveredAt +
                ", confirmedAt=" + confirmedAt +
                '}';
    }
}
