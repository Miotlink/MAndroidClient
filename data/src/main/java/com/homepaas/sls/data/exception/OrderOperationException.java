package com.homepaas.sls.data.exception;

/**
 * Created by CJJ on 2016/7/14.
 */
public class OrderOperationException extends Exception{

    public OrderOperationException(String errorMsg) {
        super(errorMsg);
    }
}
