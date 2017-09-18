package org.zh.exception;

/**
 * @author ZhaoHang
 */
public class AccountBannedException extends AuthorizationException {

    public AccountBannedException() {
        super();
    }

    public AccountBannedException(String message) {
        super(message);
    }

    public AccountBannedException(Throwable cause) {
        super(cause);
    }

    public AccountBannedException(String message, Throwable cause) {
        super(message, cause);
    }
}
