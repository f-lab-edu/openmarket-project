package oort.cloud.openmarket.exception.business;

import oort.cloud.openmarket.exception.business.BusinessException;
import oort.cloud.openmarket.exception.enums.ErrorType;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(ErrorType.USER_NOT_FOUND);
    }
}
