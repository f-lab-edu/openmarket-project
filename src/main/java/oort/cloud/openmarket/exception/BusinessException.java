package oort.cloud.openmarket.exception;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class BusinessException extends RuntimeException{
    private final ErrorType errorType;

    public BusinessException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public BusinessException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
