package oort.cloud.openmarket.common.exception.business;

import oort.cloud.openmarket.common.exception.enums.ErrorType;

public class ExternalApiException extends BusinessException{

    public ExternalApiException(ErrorType errorType) {
        super(errorType);
    }

    public ExternalApiException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}
