package oort.cloud.openmarket.exception.response;


import oort.cloud.openmarket.exception.enums.ErrorType;

import java.util.List;

public class RequestValidationErrorResponse {
    private List<FieldErrorDetail> errors;
    private ErrorType errorType;

    public static RequestValidationErrorResponse of(List<FieldErrorDetail> errors, ErrorType exceptionType) {
        RequestValidationErrorResponse response = new RequestValidationErrorResponse();
        response.errors = errors;
        response.errorType = exceptionType;
        return response;
    }

    private RequestValidationErrorResponse() {
    }

    public List<FieldErrorDetail> getErrors() {
        return errors;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public static class FieldErrorDetail {
        private String field;
        private String message;

        public FieldErrorDetail(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}
