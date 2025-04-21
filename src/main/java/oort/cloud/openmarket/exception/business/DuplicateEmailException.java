package oort.cloud.openmarket.exception.business;

import oort.cloud.openmarket.exception.business.BusinessException;
import oort.cloud.openmarket.exception.enums.ErrorType;

public class DuplicateEmailException extends BusinessException {
    public DuplicateEmailException() {
        super(ErrorType.DUPLICATE_EMAIL);
    }
}
