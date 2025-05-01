package oort.cloud.openmarket.payment.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import oort.cloud.openmarket.payment.enums.PaymentMethod;

public class PaymentCreateRequest {
    @NotEmpty
    private PaymentMethod paymentMethod;

    @NotNull
    private int amount;

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
