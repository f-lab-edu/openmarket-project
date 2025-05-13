package oort.cloud.openmarket.common.exception.auth;

import oort.cloud.openmarket.common.exception.enums.ErrorType;

public class ExpiredTokenException extends AuthenticationException {
    public ExpiredTokenException(ErrorType errorType) {
        super(errorType);
    }
}
