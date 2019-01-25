package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
public class Recharge {


    /**
     * TotalMoney : 100
     * NeedPay : 0.01
     * Activity : 1
     * Description :
     */

    @SerializedName("TotalMoney")
    private String totalMoney;
    @SerializedName("NeedPay")
    private String needPay;
    @SerializedName("Activity")
    private String activity;
    @SerializedName("Description")
    private String description;

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getNeedPay() {
        return needPay;
    }

    public void setNeedPay(String needPay) {
        this.needPay = needPay;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
