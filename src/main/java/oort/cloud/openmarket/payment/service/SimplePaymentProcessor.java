package oort.cloud.openmarket.payment.service;

import oort.cloud.openmarket.payment.service.request.SimplePaymentApiRequest;
import oort.cloud.openmarket.payment.service.response.SimplePaymentApiResponse;
import org.springframework.stereotype.Component;

/**
 *  단순히 결제 성공 응답을 반환하는 클래스
 */
@Component
public class SimplePaymentProcessor implements PaymentProcessor{

    @Override
    public SimplePaymentApiResponse process(SimplePaymentApiRequest request) {
        return new SimplePaymentApiResponse();
    }
}
