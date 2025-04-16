package oort.cloud.openmarket.exception.auth;

import oort.cloud.openmarket.exception.ApiBaseException;
import oort.cloud.openmarket.exception.enums.ErrorType;

public class AuthenticationException extends ApiBaseException {
    public AuthenticationException(ErrorType errorType) {
        super(errorType);
    }
}
