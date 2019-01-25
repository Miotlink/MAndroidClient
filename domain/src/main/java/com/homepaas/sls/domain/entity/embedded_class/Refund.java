package com.homepaas.sls.domain.entity.embedded_class;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CJJ on 2016/9/11.
 *
 */

public class Refund {

    @SerializedName("RefundTime")
    public String refundTime;
    @SerializedName("RefundAmount")
    public String refundAmount;
    @SerializedName("Status")
    public String status;//1：退款审核中，2：退款成功:4:审核不通过
    @SerializedName("LostIncome")
    public String lostIncome;
/*    @SerializedName("OrderId")
    public String orderId;
    @SerializedName("UserId")
    public String userId;*/

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getLostIncome() {
        return lostIncome;
    }

    public void setLostIncome(String lostIncome) {
        this.lostIncome = lostIncome;
    }

/*    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }*/

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
