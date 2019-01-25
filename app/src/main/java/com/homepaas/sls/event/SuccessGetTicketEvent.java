package com.homepaas.sls.event;

/**
 * Created by Administrator on 2016/7/12.
 */
public class SuccessGetTicketEvent {
    private String url;

    public SuccessGetTicketEvent(String url) {

        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
