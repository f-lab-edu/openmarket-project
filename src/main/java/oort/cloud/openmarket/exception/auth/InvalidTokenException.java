package oort.cloud.openmarket.exception.auth;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class InvalidTokenException extends AuthenticationException {
    public InvalidTokenException() {
        super(ErrorType.INVALID_TOKEN);
    }
}
