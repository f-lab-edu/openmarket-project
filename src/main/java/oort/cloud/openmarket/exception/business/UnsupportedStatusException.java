package oort.cloud.openmarket.exception.business;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class UnsupportedStatusException extends BusinessException {
    public UnsupportedStatusException() {
        super(ErrorType.UNSUPPORTED_STATE);
    }
}
