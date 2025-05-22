package oort.cloud.openmarket.payment.service.processor;

import oort.cloud.openmarket.payment.service.client.SimplePaymentClient;
import oort.cloud.openmarket.payment.service.data.PaymentResponse;
import oort.cloud.openmarket.payment.service.request.PaymentApproveRequest;
import oort.cloud.openmarket.payment.service.request.PaymentCancelRequest;
import oort.cloud.openmarket.payment.service.response.SimplePaymentResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 *  단순히 결제 성공 응답을 반환하는 클래스
 */
@Component
@Primary
public class SimplePaymentProcessor implements PaymentProcessor<SimplePaymentResponse>{
    private final SimplePaymentClient simplePaymentClient;

    public SimplePaymentProcessor(SimplePaymentClient simplePaymentClient) {
        this.simplePaymentClient = simplePaymentClient;
    }

    @Override
    public PaymentResponse<SimplePaymentResponse> approve(PaymentApproveRequest request) {
        SimplePaymentResponse response = simplePaymentClient.approve(request);
        return new PaymentResponse<>(response);
    }

    @Override
    public PaymentResponse<SimplePaymentResponse> cancel(String paymentKey, PaymentCancelRequest request) {
        SimplePaymentResponse response = simplePaymentClient.cancel(paymentKey, request);
        return new PaymentResponse<>(response);
    }

}
