package org.zh.exception;

/**
 * Created with IntelliJ IDEA 2017.1.2. <br/>
 * User: ZhaoHang  <br/>
 * Date: 2017/7/12  <br/>
 * Time: 11:24  <br/>
 *
 * @Description: 用一句话描述
 */
public class UserNotLoginException extends Exception {

    private Integer errorCode;

    private String message;

    public UserNotLoginException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public UserNotLoginException(Integer errorCode, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
        this.message = message;
    }

    public UserNotLoginException() {
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
