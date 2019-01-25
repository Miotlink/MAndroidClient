package com.homepaas.sls.domain.entity;

/**
 * Created by JWC on 2017/8/30.
 */

public class OrderStatusLayoutEntity {
    private boolean isShow;

    public OrderStatusLayoutEntity(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
