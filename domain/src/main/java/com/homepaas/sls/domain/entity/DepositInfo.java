package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/7/20.
 */

public class DepositInfo {
    //定金金额
    @SerializedName("DepositAmount")
    private String depositAmount;
    // 定金是否已付清 0：未支付 1：已支付 如果是未支付，则进行第一次，定金支付。如果已支付，则进行第二次，订单支付
    @SerializedName("DepositIsPayOff")
    private String depositIsPayOff;

    public String getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(String depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getDepositIsPayOff() {
        return depositIsPayOff;
    }

    public void setDepositIsPayOff(String depositIsPayOff) {
        this.depositIsPayOff = depositIsPayOff;
    }
}
