package oort.cloud.openmarket.common.exception.business;

import oort.cloud.openmarket.common.exception.enums.ErrorType;

public class InvalidPasswordException extends BusinessException {
    public InvalidPasswordException() {
        super(ErrorType.INVALID_PASSWORD);
    }
}
