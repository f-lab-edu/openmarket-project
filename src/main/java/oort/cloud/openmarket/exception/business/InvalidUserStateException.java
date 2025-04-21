package oort.cloud.openmarket.exception.business;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class InvalidUserStateException extends BusinessException {
    public InvalidUserStateException() {
        super(ErrorType.INVALID_USER_STATE);
    }
}
