package oort.cloud.openmarket.payment.entity;

import jakarta.persistence.*;
import oort.cloud.openmarket.order.entity.Order;
import oort.cloud.openmarket.payment.enums.PaymentMethod;
import oort.cloud.openmarket.payment.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "amount")
    private int amount;

    @Column(name = "status")
    private PaymentStatus status;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    protected Payment(){}

    public static Payment createPayment(Order order, PaymentMethod paymentMethod, int mount, PaymentStatus status){
        Payment payment = new Payment();
        payment.order = order;
        payment.paymentMethod = paymentMethod;
        payment.status = status;
        payment.paidAt = LocalDateTime.now();
        return payment;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", order=" + order +
                ", paymentMethod=" + paymentMethod +
                ", amount=" + amount +
                ", status=" + status +
                ", paidAt=" + paidAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(paymentId, payment.paymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId);
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Order getOrder() {
        return order;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public int getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }
}
