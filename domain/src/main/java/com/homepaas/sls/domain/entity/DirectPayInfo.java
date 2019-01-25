package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/7/5.
 */
public class DirectPayInfo {

    @SerializedName("Id")
    String directPayId;

    public String getDirectPayId() {
        return directPayId;
    }

    public void setDirectPayId(String directPayId) {
        this.directPayId = directPayId;
    }
}
