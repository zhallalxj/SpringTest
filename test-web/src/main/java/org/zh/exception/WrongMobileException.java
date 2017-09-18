package org.zh.exception;

/**
 * @author ZhaoHang
 */
public class WrongMobileException extends AuthorizationException {

    public WrongMobileException() {
        super();
    }

    public WrongMobileException(String message) {
        super(message);
    }

    public WrongMobileException(Throwable cause) {
        super(cause);
    }

    public WrongMobileException(String message, Throwable cause) {
        super(message, cause);
    }
}
