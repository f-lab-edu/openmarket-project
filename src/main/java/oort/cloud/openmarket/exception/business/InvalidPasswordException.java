package oort.cloud.openmarket.exception.business;

import oort.cloud.openmarket.exception.business.BusinessException;
import oort.cloud.openmarket.exception.enums.ErrorType;

public class InvalidPasswordException extends BusinessException {
    public InvalidPasswordException(ErrorType errorType) {
        super(errorType);
    }
}
