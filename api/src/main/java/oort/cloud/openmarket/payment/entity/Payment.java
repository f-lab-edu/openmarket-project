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
    @JoinColumn(name = "order_id", unique = true, nullable = false)
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

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    @Column(name = "cancel_reason")
    private String cancelReason;

    protected Payment(){}

    public static Payment createPayment(
            Order order,
            String externalOrderId,
            String paymentKey,
            String paymentMethod,
            int amount,
            PaymentStatus status,
            LocalDateTime approvedAt,
            LocalDateTime requestedAt){
        Payment payment = new Payment();
        payment.order = order;
        payment.externalOrderId = externalOrderId;
        payment.paymentKey = paymentKey;
        payment.paymentMethod = PaymentMethod.fromLabel(paymentMethod);
        payment.amount = amount;
        payment.status = status;
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
                ", canceledAt=" + canceledAt +
                ", cancelReason='" + cancelReason + '\'' +
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

    public LocalDateTime getCanceledAt(){
        return canceledAt;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public void setCanceledAt(LocalDateTime canceledAt){
        this.canceledAt = canceledAt;
    }

    public void setCancelReason(String cancelReason){
        this.cancelReason = cancelReason;
    }

}
