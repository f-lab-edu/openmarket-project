package oort.cloud.openmarket.common.exception.auth;

import oort.cloud.openmarket.common.exception.business.BusinessException;
import oort.cloud.openmarket.common.exception.enums.ErrorType;

public class UnauthorizedAccessException extends BusinessException {
    public UnauthorizedAccessException() {
        super(ErrorType.UNAUTHORIZED_ACCESS);
    }
}
