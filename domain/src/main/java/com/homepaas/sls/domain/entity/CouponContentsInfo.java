package com.homepaas.sls.domain.entity;

import java.util.List;

/**
 * Created by Sherily on 2016/6/20.
 * 优惠券数据列表模型
 */
public class CouponContentsInfo {
    private String token;
    private List<CouponContents> couponContentsList;

    public List<CouponContents> getCouponContentsList() {
        return couponContentsList;
    }

    public void setCouponContentsList(List<CouponContents> couponContentsList) {
        this.couponContentsList = couponContentsList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
