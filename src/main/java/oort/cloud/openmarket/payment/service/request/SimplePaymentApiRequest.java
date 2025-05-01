package oort.cloud.openmarket.payment.service.request;

import oort.cloud.openmarket.payment.enums.PaymentMethod;

public class SimplePaymentApiRequest {
    private Long orderId;
    private PaymentMethod paymentMethod;
    private int amount;

    public Long getOrderId() {
        return orderId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public int getAmount() {
        return amount;
    }
}
