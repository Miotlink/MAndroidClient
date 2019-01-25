package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import javax.xml.ws.ServiceMode;

/**
 * Created by Administrator on 2016/9/28.
 */

public class AddressIdEntity {
    @SerializedName("AddressId")
    String id;

    public AddressIdEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
