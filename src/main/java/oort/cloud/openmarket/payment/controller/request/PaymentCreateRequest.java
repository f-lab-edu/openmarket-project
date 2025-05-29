package oort.cloud.openmarket.payment.controller.request;

import jakarta.validation.constraints.NotNull;

public class PaymentCreateRequest {
    @NotNull
    private String externalOrderId;

    @NotNull
    private int amount;

    public String getExternalOrderId() {
        return externalOrderId;
    }

    public int getAmount() {
        return amount;
    }

}
