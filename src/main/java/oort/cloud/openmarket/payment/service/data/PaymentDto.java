package oort.cloud.openmarket.payment.service.data;

import java.time.LocalDateTime;

public class PaymentDto {
    private final String paymentKey;
    private final String externalOrderId;
    private final String method;
    private final String status;
    private final int totalAmount;
    private final LocalDateTime requestedAt;
    private final LocalDateTime approvedAt;

    private PaymentDto(Builder builder) {
        this.paymentKey = builder.paymentKey;
        this.externalOrderId = builder.externalOrderId;
        this.method = builder.method;
        this.status = builder.status;
        this.totalAmount = builder.totalAmount;
        this.approvedAt = builder.approvedAt;
        this.requestedAt = builder.requestedAt;
    }

    public static class Builder{
        private String paymentKey;
        private String externalOrderId;
        private String method;
        private String status;
        private int totalAmount;
        private LocalDateTime requestedAt;
        private LocalDateTime approvedAt;

        public Builder paymentKey(String paymentKey){
            this.paymentKey = paymentKey;
            return this;
        }

        public Builder orderId(String orderId){
            this.externalOrderId = orderId;
            return this;
        }

        public Builder method(String method){
            this.method = method;
            return this;
        }

        public Builder status(String status){
            this.status = status;
            return this;
        }

        public Builder totalAmount(int totalAmount){
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder requestedAt(LocalDateTime requestedAt){
            this.requestedAt = requestedAt;
            return this;
        }

        public Builder approvedAt(LocalDateTime approvedAt){
            this.approvedAt = approvedAt;
            return this;
        }

        public PaymentDto build(){
            return new PaymentDto(this);
        }
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

    public String getStatus() {
        return status;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    @Override
    public String toString() {
        return "PaymentDto{" +
                "paymentKey='" + paymentKey + '\'' +
                ", orderId='" + externalOrderId + '\'' +
                ", method='" + method + '\'' +
                ", status='" + status + '\'' +
                ", totalAmount=" + totalAmount +
                ", requestedAt=" + requestedAt +
                ", approvedAt=" + approvedAt +
                '}';
    }
}
