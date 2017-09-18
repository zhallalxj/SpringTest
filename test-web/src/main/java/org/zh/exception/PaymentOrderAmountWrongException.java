package org.zh.exception;

/**
 * @author ZhaoHang
 */
public class PaymentOrderAmountWrongException extends AuthorizationException {

    public PaymentOrderAmountWrongException() {
        super();
    }

    public PaymentOrderAmountWrongException(String message) {
        super(message);
    }

    public PaymentOrderAmountWrongException(Throwable cause) {
        super(cause);
    }

    public PaymentOrderAmountWrongException(String message, Throwable cause) {
        super(message, cause);
    }
}
