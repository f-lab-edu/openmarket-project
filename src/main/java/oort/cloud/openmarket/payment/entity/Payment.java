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

    @Column(name = "external_order_id")
    private String externalOrderId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "payment_key")
    private String paymentKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "amount")
    private int amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    protected Payment(){}

    public static Payment createPayment(
            Order order,
            String externalOrderId,
            String paymentKey,
            String paymentMethod,
            int amount,
            String status,
            LocalDateTime approvedAt,
            LocalDateTime requestedAt){
        Payment payment = new Payment();
        payment.order = order;
        payment.externalOrderId = externalOrderId;
        payment.paymentKey = paymentKey;
        payment.paymentMethod = PaymentMethod.fromLabel(paymentMethod);
        payment.amount = amount;
        payment.status = PaymentStatus.valueOf(status);
        payment.approvedAt = approvedAt;
        payment.requestedAt = requestedAt;
        return payment;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", externalOrderId='" + externalOrderId + '\'' +
                ", order=" + order +
                ", paymentKey='" + paymentKey + '\'' +
                ", paymentMethod=" + paymentMethod +
                ", amount=" + amount +
                ", status=" + status +
                ", approvedAt=" + approvedAt +
                ", requestedAt=" + requestedAt +
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

    public Long getPaymentId() {
        return paymentId;
    }

    public Order getOrder() {
        return order;
    }

    public String getPaymentKey() {
        return paymentKey;
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

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public String getExternalOrderId() {
        return externalOrderId;
    }
}
