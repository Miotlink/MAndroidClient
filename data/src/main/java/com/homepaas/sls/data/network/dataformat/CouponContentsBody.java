package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.data.entity.CouponContentsEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/6/23.
 */
public class CouponContentsBody {

    @SerializedName("CouponList")
    private List<CouponContentsEntity> couponContentsEntities;
    @SerializedName("Token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<CouponContentsEntity> getCouponContentsEntities() {
        return couponContentsEntities;
    }

    public void setCouponContentsEntities(List<CouponContentsEntity> couponContentsEntities) {
        this.couponContentsEntities = couponContentsEntities;
    }
}
