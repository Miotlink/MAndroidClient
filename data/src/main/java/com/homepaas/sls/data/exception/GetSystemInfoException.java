package com.homepaas.sls.data.exception;

/**
 * on 2016/2/2 0002
 *
 * @author zhudongjie .
 */
public class GetSystemInfoException extends Exception{

    public GetSystemInfoException() {
    }

    public GetSystemInfoException(String detailMessage) {
        super(detailMessage);
    }

    public GetSystemInfoException(Throwable throwable) {
        super(throwable);
    }

    public GetSystemInfoException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
