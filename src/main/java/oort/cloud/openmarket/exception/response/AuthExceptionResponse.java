package oort.cloud.openmarket.exception.response;


import oort.cloud.openmarket.exception.ErrorType;

public class AuthExceptionResponse {
    private String message;
    private ErrorType errorType;

    public AuthExceptionResponse(String message, ErrorType errorType) {
        this.message = message;
        this.errorType = errorType;
    }

    public String getMessage() {
        return message;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
