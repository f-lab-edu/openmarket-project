package oort.cloud.openmarket.exception.business;

import oort.cloud.openmarket.exception.ApiBaseException;
import oort.cloud.openmarket.exception.enums.ErrorType;


public class BusinessException extends ApiBaseException {
    public BusinessException(ErrorType errorType) {
        super(errorType);
    }

}
