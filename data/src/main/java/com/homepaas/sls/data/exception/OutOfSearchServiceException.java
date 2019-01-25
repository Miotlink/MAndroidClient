package com.homepaas.sls.data.exception;

/**
 * on 2016/3/31 0031
 *
 * @author zhudongjie .
 */
public class OutOfSearchServiceException extends Exception{

    public OutOfSearchServiceException() {
    }

    public OutOfSearchServiceException(String detailMessage) {
        super(detailMessage);
    }

    public OutOfSearchServiceException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public OutOfSearchServiceException(Throwable throwable) {
        super(throwable);
    }
}
