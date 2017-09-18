package org.zh.exception;

/**
 * @author ZhaoHang
 */
public class TokenExpiredException extends AuthorizationException {

    public TokenExpiredException() {
        super();
    }

    public TokenExpiredException(String message) {
        super(message);
    }

    public TokenExpiredException(Throwable cause) {
        super(cause);
    }

    public TokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}
