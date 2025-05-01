package oort.cloud.openmarket.exception.business;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class NotFoundOrderException extends BusinessException{
    public NotFoundOrderException() {
        super(ErrorType.NOT_FOUND_ORDER);
    }
}
