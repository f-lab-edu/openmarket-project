package oort.cloud.openmarket.payment.service.response;

import java.time.LocalDateTime;

public class SimplePaymentResponse {
    private String paymentKey;
    private String externalOrderId;
    private String method;
    private int totalAmount;
    private String status;
    private LocalDateTime requestedAt;
    private LocalDateTime approvedAt;

    public SimplePaymentResponse(String paymentKey, String externalOrderId, String method, int totalAmount, String status, LocalDateTime requestedAt, LocalDateTime approvedAt) {
        this.paymentKey = paymentKey;
        this.externalOrderId = externalOrderId;
        this.method = method;
        this.totalAmount = totalAmount;
        this.status = status;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
    }

    public String getPaymentKey() {
        return paymentKey;
    }

    public String getExternalOrderId() {
        return externalOrderId;
    }

    public String getMethod() {
        return method;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    @Override
    public String toString() {
        return "TossPaymentResponse{" +
                "paymentKey='" + paymentKey + '\'' +
                ", orderId='" + externalOrderId + '\'' +
                ", method='" + method + '\'' +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", requestedAt=" + requestedAt +
                ", approvedAt=" + approvedAt +
                '}';
    }
}
