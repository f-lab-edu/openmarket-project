package oort.cloud.openmarket.exception.business;

import oort.cloud.openmarket.exception.business.BusinessException;
import oort.cloud.openmarket.exception.enums.ErrorType;

public class DuplicateEmailException extends BusinessException {
    public DuplicateEmailException(ErrorType errorType) {
        super(errorType);
    }
}
