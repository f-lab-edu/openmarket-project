package oort.cloud.openmarket.exception.response;

public class ExternalApiExceptionResponse {
    private String errorCode;
    private String message;

    public ExternalApiExceptionResponse(String errorCode, String message){
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ExternalApiExceptionResponse{" +
                "errorCode='" + errorCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
