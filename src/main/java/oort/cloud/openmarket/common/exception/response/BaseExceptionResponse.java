package oort.cloud.openmarket.common.exception.response;

import oort.cloud.openmarket.common.exception.enums.ErrorType;

public class BaseExceptionResponse {
    protected ErrorType errorType;

    public ErrorType getErrorType() {
        return errorType;
    }
}
