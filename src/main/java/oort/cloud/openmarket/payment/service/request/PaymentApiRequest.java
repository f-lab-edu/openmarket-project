package oort.cloud.openmarket.payment.service.request;

public class PaymentApiRequest {
    private String externalOrderId;
    private String paymentKey;
    private int amount;

    public PaymentApiRequest() {
    }

    private PaymentApiRequest(Builder builder){
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

        public PaymentApiRequest build(){
            return new PaymentApiRequest(this);
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
