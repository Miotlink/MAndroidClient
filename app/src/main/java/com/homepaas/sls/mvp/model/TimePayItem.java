package com.homepaas.sls.mvp.model;


/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
public class TimePayItem implements PayItem{

    private String time;

    public TimePayItem(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
