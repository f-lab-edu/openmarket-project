package oort.cloud.openmarket.payment.controller.response;

public class PaymentCreateResponse {
    private Long paymentId;

    public PaymentCreateResponse(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getPaymentId() {
        return paymentId;
    }
}
