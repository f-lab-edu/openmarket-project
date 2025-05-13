package oort.cloud.openmarket.common.exception.business;

import oort.cloud.openmarket.common.exception.enums.ErrorType;

public class NotFoundResourceException extends BusinessException{
    public NotFoundResourceException() {
        super(ErrorType.NOT_FOUND_RESOURCE);
    }

    public NotFoundResourceException(String message){
        super(ErrorType.NOT_FOUND_RESOURCE, message);
    }
}
