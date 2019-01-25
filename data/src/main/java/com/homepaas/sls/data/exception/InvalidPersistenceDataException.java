package com.homepaas.sls.data.exception;

/**
 * Created by Administrator on 2015/12/22.
 *
 */

public class InvalidPersistenceDataException extends Exception {

    public InvalidPersistenceDataException(Throwable throwable) {
        super(throwable);
    }

    public InvalidPersistenceDataException() {
    }

    public InvalidPersistenceDataException(String detailMessage) {
        super(detailMessage);
    }

    public InvalidPersistenceDataException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
