package oort.cloud.openmarket.common.exception.business;

import oort.cloud.openmarket.common.exception.ApiBaseException;
import oort.cloud.openmarket.common.exception.enums.ErrorType;


public class BusinessException extends ApiBaseException {
    public BusinessException(ErrorType errorType) {
        super(errorType);
    }

    public BusinessException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}
