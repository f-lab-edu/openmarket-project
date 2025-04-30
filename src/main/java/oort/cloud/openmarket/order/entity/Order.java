package oort.cloud.openmarket.order.entity;

import jakarta.persistence.*;
import oort.cloud.openmarket.common.entity.BaseTimeEntity;
import oort.cloud.openmarket.common.exception.business.UnsupportedStatusException;
import oort.cloud.openmarket.order.enums.OrderStatus;
import oort.cloud.openmarket.user.entity.Address;
import oort.cloud.openmarket.user.entity.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "total_amount")
    private int totalAmount;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_phone")
    private String receiverPhone;

    protected Order(){}

    public static Order createOrder(Users user,
                                    Address address,
                                    String receiverName,
                                    String receiverPhone,
                                    List<OrderItem> orderItems){
        Order order = new Order();
        order.user = user;
        order.address = address;
        order.status = OrderStatus.CREATED;
        order.receiverName = receiverName;
        order.receiverPhone = receiverPhone;
        for (OrderItem orderItem : orderItems) {
            order.totalAmount += orderItem.getTotalPrice();
            order.addOrderItem(orderItem);
        }
        return order;
    }

    /**
     *  주문 취소는 주문 상태가 CREATED, PAYMENT_PANDING, PAID 상태일 경우 가능
     */
    public void cancel(){
        if(!status.isCancellable()){
            throw new UnsupportedStatusException();
        }
        this.setStatus(OrderStatus.CANCELLED);
        orderItems.forEach(OrderItem::cancel);
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + user +
                ", address=" + address +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                '}';
    }

    public Long getOrderId() {
        return orderId;
    }

    public Users getUser() {
        return user;
    }

    public Address getAddress() {
        return address;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
