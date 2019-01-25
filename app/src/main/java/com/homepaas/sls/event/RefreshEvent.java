package com.homepaas.sls.event;

/**
 * Created by Administrator on 2016/9/28.
 */

public class RefreshEvent {
    private  boolean isRefresh;

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public RefreshEvent(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public RefreshEvent() {
    }
}
