package oort.cloud.openmarket.exception.business;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class OutOfStockException extends BusinessException {
    public OutOfStockException() {
        super(ErrorType.OUT_OF_STOCK);
    }
}
