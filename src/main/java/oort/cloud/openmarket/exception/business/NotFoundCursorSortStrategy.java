package oort.cloud.openmarket.exception.business;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class NotFoundCursorSortStrategy extends BusinessException{
    public NotFoundCursorSortStrategy(ErrorType errorType) {
        super(errorType);
    }
}
