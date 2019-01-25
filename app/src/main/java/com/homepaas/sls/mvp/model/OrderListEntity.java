package com.homepaas.sls.mvp.model;

import com.homepaas.sls.domain.entity.OrderEntity;

import java.util.List;

/**
 * Created by mhy on 2017/8/17.
 */

public class OrderListEntity {
    private List<OrderEntity> orderEntities;

    public List<OrderEntity> getOrderEntities() {
        return orderEntities;
    }

    public void setOrderEntities(List<OrderEntity> orderEntities) {
        this.orderEntities = orderEntities;
    }
}
