package org.zh.exception;

/**
 * @author ZhaoHang
 */
public class AccountNotExistException extends AuthorizationException {

    public AccountNotExistException() {
        super();
    }

    public AccountNotExistException(String message) {
        super(message);
    }

    public AccountNotExistException(Throwable cause) {
        super(cause);
    }

    public AccountNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
