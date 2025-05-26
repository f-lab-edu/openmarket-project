package oort.cloud.openmarket.payment.service.response;

public class ExternalApiErrorResponse {
    private String errorCode;
    private String message;

    public ExternalApiErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
