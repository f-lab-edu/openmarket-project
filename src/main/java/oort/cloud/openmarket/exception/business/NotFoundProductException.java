package oort.cloud.openmarket.exception.business;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class NotFoundProductException extends BusinessException{
    public NotFoundProductException(ErrorType errorType) {
        super(errorType);
    }
}
