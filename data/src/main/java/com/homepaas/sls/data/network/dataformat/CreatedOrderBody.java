package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/4/28.
 */
public class CreatedOrderBody {
    @SerializedName("OrderId")
    String orderId;


    public CreatedOrderBody() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderCode(String orderId) {
        this.orderId = orderId;
    }
}
