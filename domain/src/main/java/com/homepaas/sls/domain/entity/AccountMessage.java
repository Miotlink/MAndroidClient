package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/10/26.
 */

public class AccountMessage {

    @SerializedName("key")
    private String key;
    @SerializedName("value")
    private String value;


    public AccountMessage(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
