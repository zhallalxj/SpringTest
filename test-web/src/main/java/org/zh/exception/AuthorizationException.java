package org.zh.exception;

/**
 * @author ZhaoHang
 */
public class AuthorizationException extends Exception {

    public AuthorizationException() {
        super();
    }

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(Throwable cause) {
        super(cause);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
