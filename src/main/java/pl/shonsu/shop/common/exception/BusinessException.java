package pl.shonsu.shop.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public abstract class BusinessException extends RuntimeException{
    public BusinessException(String msg) {
        super(msg);
    }
}
