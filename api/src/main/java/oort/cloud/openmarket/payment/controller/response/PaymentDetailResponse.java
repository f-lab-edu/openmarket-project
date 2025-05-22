package oort.cloud.openmarket.payment.controller.response;

import oort.cloud.openmarket.payment.entity.Payment;
import oort.cloud.openmarket.payment.enums.PaymentMethod;
import oort.cloud.openmarket.payment.enums.PaymentStatus;

import java.time.LocalDateTime;

public class PaymentDetailResponse {
    private Long paymentId;
    private Long orderId;
    private PaymentMethod paymentMethod;
    private int amount;
    private PaymentStatus status;
    private LocalDateTime approvedAt;

    public PaymentDetailResponse(Payment payment){
        this.paymentId = payment.getPaymentId();
        this.orderId = payment.getOrder().getOrderId();
        this.paymentMethod = payment.getPaymentMethod();
        this.amount = payment.getAmount();
        this.status = payment.getStatus();
        this.approvedAt = payment.getApprovedAt();
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Long getOrderId() {
        return orderId;
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

    @Override
    public String toString() {
        return "PaymentDetailResponse{" +
                "paymentId=" + paymentId +
                ", orderId=" + orderId +
                ", paymentMethod=" + paymentMethod +
                ", amount=" + amount +
                ", status=" + status +
                ", approvedAt=" + approvedAt +
                '}';
    }
}
