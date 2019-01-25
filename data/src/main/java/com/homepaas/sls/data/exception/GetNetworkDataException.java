package com.homepaas.sls.data.exception;

/**
 * Created by Administrator on 2015/12/23.
 *
 */

public class GetNetworkDataException extends Exception {

    public GetNetworkDataException(Throwable throwable) {
        super(throwable);
    }

    public GetNetworkDataException() {
    }

    public GetNetworkDataException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public GetNetworkDataException(String detailMessage) {
        super(detailMessage);
    }
}
