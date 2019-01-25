package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/3.
 */
public class OrderInfo implements Serializable{

    @SerializedName("OrderList")
    List<OrderEntity> orders = new ArrayList<>();

    public OrderInfo() {
    }

    public OrderInfo(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }
}
