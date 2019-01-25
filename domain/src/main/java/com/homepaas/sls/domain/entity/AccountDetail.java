package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
public class AccountDetail {


    @SerializedName("Id")
    private String id;

    @SerializedName("Code")
    private String code;

    @SerializedName("Time")
    private String time;

    @SerializedName("OperateType")
    private String operateType;

    @SerializedName("Money")
    private String money;

    @SerializedName("Remark")
    private String remark;

    @SerializedName("PaymentMode")
    private String paymentMode;

//    @SerializedName("OwnerId")
//    private String ownerId;
//
//    @SerializedName("OperateOwnerId")
//    private String operatorOwnerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
//    public String getOwnerId() {
//        return ownerId;
//    }
//
//    public void setOwnerId(String ownerId) {
//        this.ownerId = ownerId;
//    }
//
//    public String getOperatorOwnerId() {
//        return operatorOwnerId;
//    }
//
//    public void setOperatorOwnerId(String operatorOwnerId) {
//        this.operatorOwnerId = operatorOwnerId;
//    }
}
