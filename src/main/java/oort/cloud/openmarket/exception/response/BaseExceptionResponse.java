package oort.cloud.openmarket.exception.response;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class BaseExceptionResponse {
    protected ErrorType errorType;

    public ErrorType getErrorType() {
        return errorType;
    }
}
