package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.WxSign;

/**
 * Created by CMJ on 2016/6/28.
 */

public class OrderPayWXSignBody {

    @SerializedName("WxpaySign")
    WxSign wxSign;

    public OrderPayWXSignBody() {
    }

    public WxSign getWxSign() {
        return wxSign;
    }

    public void setWxSign(WxSign wxSign) {
        this.wxSign = wxSign;
    }
}
