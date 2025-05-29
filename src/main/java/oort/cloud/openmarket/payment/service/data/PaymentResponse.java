package oort.cloud.openmarket.payment.service.data;

public class PaymentResponse<T> {
    private final T data;

    public PaymentResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
