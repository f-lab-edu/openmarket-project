package oort.cloud.openmarket.exception.business;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class NotFoundCategoryException extends BusinessException{
    public NotFoundCategoryException(ErrorType errorType) {
        super(errorType);
    }
}
