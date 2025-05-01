package oort.cloud.openmarket.payment.service;

import oort.cloud.openmarket.payment.service.request.SimplePaymentApiRequest;
import oort.cloud.openmarket.payment.service.response.SimplePaymentApiResponse;

public interface PaymentProcessor {
    SimplePaymentApiResponse process(SimplePaymentApiRequest request);
}
