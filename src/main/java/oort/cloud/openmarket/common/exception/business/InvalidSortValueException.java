package oort.cloud.openmarket.common.exception.business;

import oort.cloud.openmarket.common.exception.enums.ErrorType;

public class InvalidSortValueException extends BusinessException{
    public InvalidSortValueException() {
        super(ErrorType.INVALID_SORT_VALUE);
    }
}
