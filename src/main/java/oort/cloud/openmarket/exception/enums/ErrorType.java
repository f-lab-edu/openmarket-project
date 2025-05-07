package oort.cloud.openmarket.exception.enums;

import org.springframework.http.HttpStatus;

public enum ErrorType{
    UNKNOWN("알 수 없는 오류 입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_ERROR("시스템 내부에 문제가 발생 했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PARAMETER("잘못된 파라미터 입니다.", HttpStatus.BAD_REQUEST),
    DUPLICATE_EMAIL("이미 존재하는 이메일 입니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("가입된 회원정보가 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD("비밀번호가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("만료된 토큰 입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_ACCESS("인가되지 않은 접근입니다.", HttpStatus.UNAUTHORIZED),
    NOT_FOUND_REFRESH_TOKEN("저장된 토큰 정보가 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_PRODUCT("저장된 상품 정보가 없습니다", HttpStatus.BAD_REQUEST),
    NOT_FOUND_CATEGORY("저장된 카테고리 정보가 없습니다", HttpStatus.BAD_REQUEST),
    NOT_FOUND_SORT_STRATEGY("제공하는 정렬 조건이 아닙니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_ADDRESS("등록된 주소 정보가 없습니다.", HttpStatus.BAD_REQUEST),
    UNSUPPORTED_STATE("지원되지 않는 상태 입니다.", HttpStatus.BAD_REQUEST),
    OUT_OF_STOCK("주문 상품의 재고가 부족합니다.", HttpStatus.BAD_REQUEST)

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
