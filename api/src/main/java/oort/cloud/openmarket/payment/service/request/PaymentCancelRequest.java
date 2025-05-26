package oort.cloud.openmarket.payment.service.request;

public class PaymentCancelRequest {
    private String cancelReason;

    public PaymentCancelRequest(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getCancelReason() {
        return cancelReason;
    }
}
