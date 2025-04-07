package oort.cloud.openmarket.exception;

public class AuthServiceException extends RuntimeException{
    private ErrorType errorType;

    public AuthServiceException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
    public AuthServiceException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
