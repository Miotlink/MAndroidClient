package com.homepaas.sls.data.exception;

/**
 * Created by Administrator on 2015/12/23.
 * 表示用户权限认证失败异常
 */

public class ResponseMetaAuthException extends Exception {

    public ResponseMetaAuthException() {
    }

    public ResponseMetaAuthException(String detailMessage) {
        super(detailMessage);
    }

    public ResponseMetaAuthException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ResponseMetaAuthException(Throwable throwable) {
        super(throwable);
    }
}
