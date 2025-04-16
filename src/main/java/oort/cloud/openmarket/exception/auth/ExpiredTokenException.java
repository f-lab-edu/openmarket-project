package oort.cloud.openmarket.exception.auth;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class ExpiredTokenException extends AuthenticationException {
    public ExpiredTokenException(ErrorType errorType) {
        super(errorType);
    }
}
