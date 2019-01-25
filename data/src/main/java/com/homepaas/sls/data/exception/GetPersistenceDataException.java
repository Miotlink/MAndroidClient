package com.homepaas.sls.data.exception;

/**
 * Created by Administrator on 2015/12/22.
 *
 */

public class GetPersistenceDataException extends Exception {

    public GetPersistenceDataException() {
    }

    public GetPersistenceDataException(String detailMessage) {
        super(detailMessage);
    }

    public GetPersistenceDataException(Throwable throwable) {
        super(throwable);
    }
}
