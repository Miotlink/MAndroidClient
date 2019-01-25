package com.homepaas.sls.data.exception;

/**
 * Created by Administrator on 2015/12/22.
 *
 */

public class PersistDataException extends Exception {

    public PersistDataException() {
    }

    public PersistDataException(Throwable throwable) {
        super(throwable);
    }

    public PersistDataException(String detailMessage) {
        super(detailMessage);
    }
}
