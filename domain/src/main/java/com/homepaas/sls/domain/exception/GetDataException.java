package com.homepaas.sls.domain.exception;

/**
 * Created by Administrator on 2015/12/22.
 */

public class GetDataException extends Exception {
    private String errorCode;

    private GetDataException() {
    }

    public GetDataException(String message) {
        super(message);
    }


    public GetDataException(Throwable cause) {
        super(cause);
    }

    public GetDataException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public GetDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetDataException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
