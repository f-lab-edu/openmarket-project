package oort.cloud.openmarket.payment.service.processor;

import oort.cloud.openmarket.payment.service.data.PaymentDto;
import oort.cloud.openmarket.payment.service.request.PaymentApiRequest;

public interface PaymentProcessor{
    PaymentDto process(PaymentApiRequest request);
}
