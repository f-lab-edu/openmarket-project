package oort.cloud.openmarket.common.exception;

import oort.cloud.openmarket.common.exception.enums.ErrorType;

public class ApiBaseException extends RuntimeException{
    private final ErrorType errorType;
    public ApiBaseException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ApiBaseException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
