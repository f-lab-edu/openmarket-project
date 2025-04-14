package oort.cloud.openmarket.exception.enums;

import org.springframework.http.HttpStatus;

public enum ErrorType{
    UNKNOWN("알 수 없는 오류 입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PARAMETER("잘못된 파라미터 입니다.", HttpStatus.BAD_REQUEST),
    DUPLICATE_EMAIL("이미 존재하는 이메일 입니다.", HttpStatus.BAD_REQUEST),
    ;
    private final String message;
    private final HttpStatus status;

    ErrorType(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {return message;}

    public HttpStatus getStatus() {
        return status;
    }
}
