package oort.cloud.openmarket.exception.external;

import oort.cloud.openmarket.exception.ExternalApiException;
import org.springframework.http.HttpStatus;

public class PaymentApiException extends ExternalApiException {
    public PaymentApiException(String errorCode, String message, int httpStatus) {
        super(errorCode, message, httpStatus);
    }
}
