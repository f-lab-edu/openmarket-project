package oort.cloud.openmarket.payment.service.request;

public class PaymentApproveRequest {
    private final String externalOrderId;
    private final String paymentKey;
    private final int amount;

    private PaymentApproveRequest(Builder builder){
        this.externalOrderId = builder.orderId;
        this.paymentKey = builder.paymentKey;
        this.amount = builder.amount;
    }

    public static class Builder{
        private String orderId;
        private String paymentKey;
        private int amount;

        public Builder paymentKey(String paymentKey){
            this.paymentKey = paymentKey;
            return this;
        }

        public Builder orderId(String orderId){
            this.orderId = orderId;
            return this;
        }

        public Builder amount(int amount){
            this.amount = amount;
            return this;
        }

        public PaymentApproveRequest build(){
            return new PaymentApproveRequest(this);
        }
    }
    public String getExternalOrderId() {
        return externalOrderId;
    }

    public String getPaymentKey() {
        return paymentKey;
    }

    public int getAmount() {
        return amount;
    }
}
