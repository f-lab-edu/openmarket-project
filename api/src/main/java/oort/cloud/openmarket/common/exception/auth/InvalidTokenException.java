package oort.cloud.openmarket.common.exception.auth;

import oort.cloud.openmarket.common.exception.enums.ErrorType;

public class InvalidTokenException extends AuthenticationException {
    public InvalidTokenException(ErrorType errorType) {
        super(errorType);
    }
}
