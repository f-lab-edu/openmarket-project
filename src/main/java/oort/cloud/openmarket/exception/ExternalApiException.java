package oort.cloud.openmarket.exception;

public class ExternalApiException extends RuntimeException{
    private String errorCode;
    private String message;
    private int httpStatus;

    public ExternalApiException(String errorCode, String message, int httpStatus){
        super(message);
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return httpStatus;
    }
}
