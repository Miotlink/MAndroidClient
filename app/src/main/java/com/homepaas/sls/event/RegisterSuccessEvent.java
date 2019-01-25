package com.homepaas.sls.event;

/**
 * Created by Administrator on 2016/7/12.
 */
public class RegisterSuccessEvent {
    public String token;
    public String url;

    public RegisterSuccessEvent(String token, String url) {
        this.url = url;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
