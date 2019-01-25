package com.homepaas.sls.domain.exception;

/**
 * on 2016/1/21 0021
 *
 * @author zhudongjie .
 */
public class NotNormalDeviceException extends Exception{

    public NotNormalDeviceException(String message) {
        super(message);
    }

    public NotNormalDeviceException(Throwable cause) {
        super(cause);
    }

    public NotNormalDeviceException() {
    }
}
