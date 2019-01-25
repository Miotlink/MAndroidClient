package com.homepaas.sls.data.exception;

/**
 * Created by Administrator on 2015/12/23.
 *后台返回错误code异常抛出
 */

public class ResponseMetaDataException extends Exception {

    private String errorCode;
    private ResponseMetaDataException() {}

    public ResponseMetaDataException(String message) {
        super(message);
    }
    public ResponseMetaDataException(String errorCode,String message) {
        super(message);
        this.errorCode=errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
