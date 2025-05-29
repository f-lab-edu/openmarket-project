package oort.cloud.openmarket.payment.service.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import oort.cloud.openmarket.common.util.LocalDateTimeFromOffsetDateTimeDeserializer;
import oort.cloud.openmarket.payment.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class SimplePaymentResponse {
    private String paymentKey;
    private String externalOrderId;
    private String method;
    private int totalAmount;
    private PaymentStatus status;
    @JsonDeserialize(using = LocalDateTimeFromOffsetDateTimeDeserializer.class)
    private LocalDateTime requestedAt;
    @JsonDeserialize(using = LocalDateTimeFromOffsetDateTimeDeserializer.class)
    private LocalDateTime approvedAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Cancel> cancels;

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

    public PaymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public List<Cancel> getCancels() {
        return cancels;
    }

    public LocalDateTime getLatestCanceledAt(){
        if (cancels == null || cancels.isEmpty()) {
            return null;
        }

        return cancels.stream()
                .max(Comparator.comparing(Cancel::getCanceledAt))
                .orElse(null).canceledAt;
    }

    @Override
    public String toString() {
        return "SimplePaymentResponse{" +
                "paymentKey='" + paymentKey + '\'' +
                ", externalOrderId='" + externalOrderId + '\'' +
                ", method='" + method + '\'' +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", requestedAt=" + requestedAt +
                ", approvedAt=" + approvedAt +
                ", cancels=" + cancels +
                '}';
    }

    static class Cancel {
        private String transactionKey;
        private String cancelReason;
        private int cancelAmount;
        private PaymentStatus cancelStatus;
        @JsonDeserialize(using = LocalDateTimeFromOffsetDateTimeDeserializer.class)
        private LocalDateTime canceledAt;

        public String getTransactionKey() {
            return transactionKey;
        }

        public String getCancelReason() {
            return cancelReason;
        }

        public int getCancelAmount() {
            return cancelAmount;
        }

        public PaymentStatus getCancelStatus() {
            return cancelStatus;
        }


        public LocalDateTime getCanceledAt() {
            return canceledAt;
        }

        @Override
        public String toString() {
            return "Cancel{" +
                    "transactionKey='" + transactionKey + '\'' +
                    ", cancelReason='" + cancelReason + '\'' +
                    ", cancelAmount=" + cancelAmount +
                    ", cancelStatus=" + cancelStatus +
                    ", canceledAt=" + canceledAt +
                    '}';
        }
    }
}
