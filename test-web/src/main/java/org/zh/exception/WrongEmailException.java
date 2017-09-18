package org.zh.exception;

/**
 * @author ZhaoHang
 */
public class WrongEmailException extends AuthorizationException {

    public WrongEmailException() {
        super();
    }

    public WrongEmailException(String message) {
        super(message);
    }

    public WrongEmailException(Throwable cause) {
        super(cause);
    }

    public WrongEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
