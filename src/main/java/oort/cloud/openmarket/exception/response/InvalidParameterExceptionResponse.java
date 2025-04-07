package oort.cloud.openmarket.exception.response;


import oort.cloud.openmarket.exception.ErrorType;

import java.util.List;

public class InvalidParameterExceptionResponse {
    private List<FieldErrorDetail> errors;
    private ErrorType exceptionType;

    public InvalidParameterExceptionResponse(List<FieldErrorDetail> errors, ErrorType exceptionType) {
        this.errors = errors;
        this.exceptionType = exceptionType;
    }

    public List<FieldErrorDetail> getErrors() {
        return errors;
    }

    public ErrorType getExceptionType() {
        return exceptionType;
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
