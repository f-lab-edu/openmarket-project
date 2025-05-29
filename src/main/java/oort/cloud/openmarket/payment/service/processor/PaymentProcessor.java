package oort.cloud.openmarket.payment.service.processor;

import oort.cloud.openmarket.payment.service.data.PaymentResponse;
import oort.cloud.openmarket.payment.service.request.PaymentApproveRequest;
import oort.cloud.openmarket.payment.service.request.PaymentCancelRequest;

public interface PaymentProcessor<T>{
    PaymentResponse<T> approve(PaymentApproveRequest request);
    PaymentResponse<T> cancel(String paymentKey, PaymentCancelRequest request);
}
