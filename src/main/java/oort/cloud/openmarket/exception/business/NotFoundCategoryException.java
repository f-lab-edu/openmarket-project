package oort.cloud.openmarket.exception.business;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class NotFoundCategoryException extends BusinessException{
    public NotFoundCategoryException() {
        super(ErrorType.NOT_FOUND_CATEGORY);
    }
}
