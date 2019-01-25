package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.OrderEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class AllOrderBody {

    @SerializedName("OrderList")
    List<OrderEntity> orderInfos;


    public List<OrderEntity> getOrderInfos() {
        return orderInfos;
    }

    public void setOrderInfos(List<OrderEntity> orderInfos) {
        this.orderInfos = orderInfos;
    }
}
