package oort.cloud.openmarket.exception;

public enum ErrorType {
    UNKNOWN("알 수 없는 오류 입니다."),
    INVALID_PARAMETER("잘못된 파라미터 입니다."),
    DUPLICATE_EMAIL("이미 존재하는 이메일 입니다."),
    ;
    private final String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {return message;}
}
