package oort.cloud.openmarket.common.exception.business;

import oort.cloud.openmarket.common.exception.enums.ErrorType;

public class NotAllowedActionException extends BusinessException{
    public NotAllowedActionException() {
        super(ErrorType.NOT_ALLOWED);
    }

    public NotAllowedActionException(String message){
        super(ErrorType.NOT_ALLOWED, message);
    }
}
