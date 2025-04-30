package oort.cloud.openmarket.exception.business;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class NotFoundUserException extends BusinessException {
    public NotFoundUserException() {
        super(ErrorType.USER_NOT_FOUND);
    }
}
