/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.zh.exception;

/**
 *
 * @author lishaoqing
 */
public class BillingToGameResponseException extends ServiceException {

    private static final long serialVersionUID = 3583566093089790852L;

    public BillingToGameResponseException() {
        super();
    }

    public BillingToGameResponseException(String message) {
        super(message);
    }

    public BillingToGameResponseException(Throwable cause) {
        super(cause);
    }

    public BillingToGameResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
