package oort.cloud.openmarket.exception.auth;

import oort.cloud.openmarket.exception.business.BusinessException;
import oort.cloud.openmarket.exception.enums.ErrorType;

public class UnauthorizedAccessException extends BusinessException {
    public UnauthorizedAccessException(ErrorType errorType) {
        super(errorType);
    }
}
