package oort.cloud.openmarket.exception.business;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class NotFoundProductException extends BusinessException{
    public NotFoundProductException() {
        super(ErrorType.NOT_FOUND_PRODUCT);
    }
}
