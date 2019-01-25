package com.homepaas.sls.domain.exception;

/**
 * Created by Administrator on 2015/12/22.
 */

public class SaveDataException extends Exception {

    public SaveDataException() {
    }

    public SaveDataException(String message) {
        super(message);
    }

    public SaveDataException(Throwable cause) {
        super(cause);
    }

    public SaveDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
