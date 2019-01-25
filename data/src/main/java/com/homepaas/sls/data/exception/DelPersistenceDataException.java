package com.homepaas.sls.data.exception;

/**
 * Created by Administrator on 2015/12/22.
 *
 */

public class DelPersistenceDataException extends Exception {

    public DelPersistenceDataException() {
    }

    public DelPersistenceDataException(String detailMessage) {
        super(detailMessage);
    }

    public DelPersistenceDataException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public DelPersistenceDataException(Throwable throwable) {
        super(throwable);
    }
}
