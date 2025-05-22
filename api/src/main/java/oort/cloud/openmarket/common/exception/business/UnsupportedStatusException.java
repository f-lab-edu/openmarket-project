package oort.cloud.openmarket.common.exception.business;

import oort.cloud.openmarket.common.exception.enums.ErrorType;

public class UnsupportedStatusException extends BusinessException {
    public UnsupportedStatusException() {
        super(ErrorType.UNSUPPORTED_STATE);
    }
}
