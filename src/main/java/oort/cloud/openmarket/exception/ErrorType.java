package oort.cloud.openmarket.exception;

public enum ErrorType {
    UNKNOWN("알 수 없는 오류 입니다."),
    INVALID_PARAMETER("잘못된 파라미터 입니다."),
    DUPLICATE_EMAIL("이미 존재하는 이메일 입니다."),
    USER_NOT_FOUND("가입된 회원정보가 없습니다."),
    INVALID_PASSWORD("비밀번호가 올바르지 않습니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("만료된 토큰 입니다."),
    UNAUTHORIZED_ACCESS("인가되지 않은 접근입니다."),
    ;
    private final String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {return message;}
}
