package com.homepaas.sls.event;

/**
 * Created by CJJ on 2016/7/13.
 */
public class PayAbortEvent {

    public String msg;
    public int code;

    public PayAbortEvent(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public PayAbortEvent() {
    }
}
