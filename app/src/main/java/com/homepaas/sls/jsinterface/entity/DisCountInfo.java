package com.homepaas.sls.jsinterface.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/6/3.
 */

public class DisCountInfo {
    @SerializedName("TotalMoney")
    private String totalMoney;
    @SerializedName("IsNotUseCoupon")
    private String isNotUseCoupon;
    @SerializedName("ServiceId")
    private String serviceId;
    @SerializedName("CouponId")
    private String couponId;

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getIsNotUseCoupon() {
        return isNotUseCoupon;
    }

    public void setIsNotUseCoupon(String isNotUseCoupon) {
        this.isNotUseCoupon = isNotUseCoupon;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }
}
