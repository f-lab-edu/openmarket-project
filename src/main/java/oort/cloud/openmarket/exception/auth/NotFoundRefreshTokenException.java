package oort.cloud.openmarket.exception.auth;

import oort.cloud.openmarket.exception.enums.ErrorType;

public class NotFoundRefreshTokenException extends AuthenticationException{
    public NotFoundRefreshTokenException(ErrorType errorType) {
        super(errorType);
    }
}
