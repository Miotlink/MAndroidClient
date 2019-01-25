package com.homepaas.sls.data.exception;

/**
 * on 2016/3/31 0031
 *
 * @author zhudongjie .
 */
public class NoSearchServiceException extends Exception {


    public NoSearchServiceException(String detailMessage) {
        super(detailMessage);
    }

    public NoSearchServiceException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}
