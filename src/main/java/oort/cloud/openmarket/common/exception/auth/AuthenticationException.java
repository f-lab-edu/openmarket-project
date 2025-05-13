package oort.cloud.openmarket.common.exception.auth;

import oort.cloud.openmarket.common.exception.ApiBaseException;
import oort.cloud.openmarket.common.exception.enums.ErrorType;

public class AuthenticationException extends ApiBaseException {
    public AuthenticationException(ErrorType errorType) {
        super(errorType);
    }
}
