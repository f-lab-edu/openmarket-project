package oort.cloud.openmarket.common.exception.business;
import oort.cloud.openmarket.common.exception.enums.ErrorType;

public class OutOfStockException extends BusinessException {
    public OutOfStockException() {
        super(ErrorType.OUT_OF_STOCK);
    }
}
