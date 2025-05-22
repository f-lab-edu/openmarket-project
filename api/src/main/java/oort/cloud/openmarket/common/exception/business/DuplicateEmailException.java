package oort.cloud.openmarket.common.exception.business;

import oort.cloud.openmarket.common.exception.enums.ErrorType;

public class DuplicateEmailException extends BusinessException {
    public DuplicateEmailException() {
        super(ErrorType.DUPLICATE_EMAIL);
    }
}
