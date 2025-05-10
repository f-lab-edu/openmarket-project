package oort.cloud.openmarket.common.exception.response;


import oort.cloud.openmarket.common.exception.enums.ErrorType;

public class ApiExceptionResponse extends BaseExceptionResponse{
    private String message;

    public static ApiExceptionResponse of(ErrorType errorType) {
        ApiExceptionResponse exceptionResponse = new ApiExceptionResponse();
        exceptionResponse.message = errorType.getMessage();
        exceptionResponse.errorType = errorType;
        return exceptionResponse;
    }

    public static ApiExceptionResponse of(String message, ErrorType errorType) {
        ApiExceptionResponse exceptionResponse = new ApiExceptionResponse();
        exceptionResponse.message = message;
        exceptionResponse.errorType = errorType;
        return exceptionResponse;
    }

    public String getMessage() {
        return message;
    }
}
