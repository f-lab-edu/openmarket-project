package oort.cloud.openmarket.payment.service.processor;

import oort.cloud.openmarket.payment.service.client.SimplePaymentClient;
import oort.cloud.openmarket.payment.service.data.PaymentDto;
import oort.cloud.openmarket.payment.service.request.PaymentApiRequest;
import oort.cloud.openmarket.payment.service.response.SimplePaymentResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 *  단순히 결제 성공 응답을 반환하는 클래스
 */
@Component
@Primary
public class SimplePaymentProcessor implements PaymentProcessor{
    private final SimplePaymentClient simplePaymentClient;

    public SimplePaymentProcessor(SimplePaymentClient simplePaymentClient) {
        this.simplePaymentClient = simplePaymentClient;
    }

    @Override
    public PaymentDto process(PaymentApiRequest request) {
        SimplePaymentResponse simplePaymentResponse = simplePaymentClient.approvePayment(request);
        return new PaymentDto.Builder()
                .paymentKey(simplePaymentResponse.getPaymentKey())
                .orderId(simplePaymentResponse.getExternalOrderId())
                .status(simplePaymentResponse.getStatus())
                .approvedAt(simplePaymentResponse.getApprovedAt())
                .requestedAt(simplePaymentResponse.getRequestedAt())
                .totalAmount(simplePaymentResponse.getTotalAmount())
                .method(simplePaymentResponse.getMethod())
                .build();
    }

}
