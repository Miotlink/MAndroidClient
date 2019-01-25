package com.homepaas.sls.domain.exception;

/**
 * on 2016/3/31 0031
 *
 * @author zhudongjie .
 */
public class OutOfServiceException extends Exception{

    public OutOfServiceException(String message) {
        super(message);
    }

    public OutOfServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
