package org.zh.exception;

/**
 * @author ZhaoHang
 */
public class PaymentOrderNotExistException extends AuthorizationException {

    public PaymentOrderNotExistException() {
        super();
    }

    public PaymentOrderNotExistException(String message) {
        super(message);
    }

    public PaymentOrderNotExistException(Throwable cause) {
        super(cause);
    }

    public PaymentOrderNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
