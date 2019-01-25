package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/3/29.
 */

public class PlaceOrderEntry {
    @SerializedName("OrderId")
    private String orderId;
    @SerializedName("ResidualTime")
    private String residualTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getResidualTime() {
        return residualTime;
    }

    public void setResidualTime(String residualTime) {
        this.residualTime = residualTime;
    }
}
