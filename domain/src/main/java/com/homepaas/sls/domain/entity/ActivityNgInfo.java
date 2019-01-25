package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sherily on 2017/2/15.
 */

public class ActivityNgInfo implements Serializable{
    @SerializedName("ActivityNgId")
    private String id;
    @SerializedName("ServiceTypeId")
    private String serviceTypeId;
    @SerializedName("StartOn")
    private String startOn;
    @SerializedName("EndOn")
    private String endOn;
    @SerializedName("OrdersCountCondition")
    private String ordersCountCondition;
    @SerializedName("OrdersDetailCondition")
    private String ordersDetailCondition;
    @SerializedName("TotalPriceCondition")
    private String totalPriceCondition;
    /**
     * 0：见面
     * 1：返充
     * 2：红包；
     * 3：充值折扣
     * 4：充值送余额
     * 5：充值送红包
     * 6：App引流红包
     */
    @SerializedName("ReturnType")
    private String returnType;
    @SerializedName("ReturnMoney")
    private String returnMoney;
    @SerializedName("UserType")
    private String userType;
    @SerializedName("Slogan")
    private String slogan;
    @SerializedName("Help")
    private String help;
    @SerializedName("Ads")
    private String ads;
    @SerializedName("ProviderUserId")
    private String providerUserId;
    @SerializedName("ProviderUserType")
    private String providerUserType;

    @SerializedName("Title")
    private String title;
    /**
     * 活动类型，0：减免，1：返充，2：红包
     */
    @SerializedName("ActivityNgType")
    private String activityNgType;

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getStartOn() {
        return startOn;
    }

    public void setStartOn(String startOn) {
        this.startOn = startOn;
    }

    public String getEndOn() {
        return endOn;
    }

    public void setEndOn(String endOn) {
        this.endOn = endOn;
    }

    public String getOrdersCountCondition() {
        return ordersCountCondition;
    }

    public void setOrdersCountCondition(String ordersCountCondition) {
        this.ordersCountCondition = ordersCountCondition;
    }

    public String getOrdersDetailCondition() {
        return ordersDetailCondition;
    }

    public void setOrdersDetailCondition(String ordersDetailCondition) {
        this.ordersDetailCondition = ordersDetailCondition;
    }

    public String getTotalPriceCondition() {
        return totalPriceCondition;
    }

    public void setTotalPriceCondition(String totalPriceCondition) {
        this.totalPriceCondition = totalPriceCondition;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(String returnMoney) {
        this.returnMoney = returnMoney;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getAds() {
        return ads;
    }

    public void setAds(String ads) {
        this.ads = ads;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getProviderUserType() {
        return providerUserType;
    }

    public void setProviderUserType(String providerUserType) {
        this.providerUserType = providerUserType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActivityNgType() {
        return activityNgType;
    }

    public void setActivityNgType(String activityNgType) {
        this.activityNgType = activityNgType;
    }
}
