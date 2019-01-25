package com.homepaas.sls.jsinterface.entity;

/**
 * Created by Administrator on 2016/7/11.
 */
public class SuccessGetAndroidToken {
    private final String token;

    public SuccessGetAndroidToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}