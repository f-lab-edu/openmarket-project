package oort.cloud.openmarket.data;

import jakarta.validation.constraints.NotNull;
import oort.cloud.openmarket.payment.controller.request.PaymentCreateRequest;

public class PaymentCreateRequestTest extends PaymentCreateRequest {
    @NotNull
    private String externalOrderId;

    @NotNull
    private int amount;

    public PaymentCreateRequestTest(String externalOrderId, int amount) {
        this.externalOrderId = externalOrderId;
        this.amount = amount;
    }

    @Override
    public String getExternalOrderId() {
        return this.externalOrderId;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }
}
