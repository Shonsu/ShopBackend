package pl.shonsu.shop.security.exception;

import pl.shonsu.shop.common.exception.BusinessException;

public class RegisterException extends BusinessException {

    public RegisterException(String msg) {
        super(msg);
    }
}
