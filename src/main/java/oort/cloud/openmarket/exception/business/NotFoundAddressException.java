package oort.cloud.openmarket.exception.business;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class NotFoundAddressException extends BusinessException {
    public NotFoundAddressException() {
        super(ErrorType.NOT_FOUND_ADDRESS);
    }
}
