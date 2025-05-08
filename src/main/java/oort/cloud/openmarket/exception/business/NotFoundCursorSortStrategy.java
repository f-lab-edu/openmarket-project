package oort.cloud.openmarket.exception.business;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class NotFoundCursorSortStrategy extends BusinessException{
    public NotFoundCursorSortStrategy() {
        super(ErrorType.NOT_FOUNT_SORT_STRATEGY);
    }
}
