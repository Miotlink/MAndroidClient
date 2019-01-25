package com.homepaas.sls.domain.exception;

/**
 * Created by CJJ on 2016/10/25.
 *
 */

public class GetPaySignException extends Exception{

    public GetPaySignException() {
    }

    public GetPaySignException(String detailMessage) {
        super(detailMessage);
    }

    public GetPaySignException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public GetPaySignException(Throwable throwable) {
        super(throwable);
    }
}
