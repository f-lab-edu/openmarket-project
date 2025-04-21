package oort.cloud.openmarket.exception.auth;

import oort.cloud.openmarket.exception.BusinessException;
import oort.cloud.openmarket.exception.enums.ErrorType;

public class DuplicateEmailException extends BusinessException {
    public DuplicateEmailException(ErrorType errorType) {
        super(errorType);
    }
}
