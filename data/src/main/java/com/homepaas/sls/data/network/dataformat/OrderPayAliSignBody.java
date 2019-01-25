package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CMJ on 2016/6/25.
 */

public class OrderPayAliSignBody {

    @SerializedName("AlipaySign")
    public String sign;

    public OrderPayAliSignBody() {
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
