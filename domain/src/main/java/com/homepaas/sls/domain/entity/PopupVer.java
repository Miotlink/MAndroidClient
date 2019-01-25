package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/12/27.
 */

public class PopupVer {
    @SerializedName("PopupValue")
    private String popuValue;

    public String getPopuValue() {
        return popuValue;
    }

    public void setPopuValue(String popuValue) {
        this.popuValue = popuValue;
    }
}
