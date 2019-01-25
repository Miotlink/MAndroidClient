package com.homepaas.sls.domain.exception;

/**
 * on 2016/3/31 0031
 *
 * @author zhudongjie .
 */
public class NoServiceException extends Exception{

    public NoServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoServiceException(String message) {
        super(message);
    }
}
