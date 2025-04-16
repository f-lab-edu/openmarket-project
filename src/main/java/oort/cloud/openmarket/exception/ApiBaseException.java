package oort.cloud.openmarket.exception;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class ApiBaseException extends RuntimeException{
    private final ErrorType errorType;

    public ApiBaseException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
