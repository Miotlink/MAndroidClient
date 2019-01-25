package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
public class PayDetail {

//    @SerializedName("Id")
//    private String id;
//
//    @SerializedName("Code")
//    private String code;
//
//    @SerializedName("Time")
//    private String timeList;
//
//    @SerializedName("OperateType")
//    private String operateType;
//
//    @SerializedName("Money")
//    private String money;
//
//    @SerializedName("Remark")
//    private String remark;
//
//    @SerializedName("PaymentList")
//    private List<Payment> paymentList;
//
//    /**
//     * 0:无退款，1：退款中，2:退款结束
//     */
//    @SerializedName("RefundStatus")
//    private int refundStatus;
//
//    @SerializedName("RefundMoney")
//    private String refundMoney;
//
//    @SerializedName("RefundFlow")
//    private String refundFlow;
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getTimeList() {
//        return timeList;
//    }
//
//    public void setTimeList(String timeList) {
//        this.timeList = timeList;
//    }
//
//    public String getOperateType() {
//        return operateType;
//    }
//
//    public void setOperateType(String operateType) {
//        this.operateType = operateType;
//    }
//
//    public String getMoney() {
//        return money;
//    }
//
//    public void setMoney(String money) {
//        this.money = money;
//    }
//
//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
//
//    public List<Payment> getPaymentList() {
//        return paymentList;
//    }
//
//    public void setPaymentList(List<Payment> paymentList) {
//        this.paymentList = paymentList;
//    }
//
//    public int getRefundStatus() {
//        return refundStatus;
//    }
//
//    public void setRefundStatus(int refundStatus) {
//        this.refundStatus = refundStatus;
//    }
//
//    public String getRefundMoney() {
//        return refundMoney;
//    }
//
//    public void setRefundMoney(String refundMoney) {
//        this.refundMoney = refundMoney;
//    }
//
//    public String getRefundFlow() {
//        return refundFlow;
//    }
//
//    public void setRefundFlow(String refundFlow) {
//        this.refundFlow = refundFlow;
//    }
//
//    public static class Payment{
//
//        /**
//         * 0：余额,1：微信，2：支付宝，3:红包
//         */
//        @SerializedName("PaymentType")
//        private int type;
//
//        @SerializedName("Money")
//        private String money;
//
//        public int getType() {
//            return type;
//        }
//
//        public void setType(int type) {
//            this.type = type;
//        }
//
//        public String getMoney() {
//            return money;
//        }
//
//        public void setMoney(String money) {
//            this.money = money;
//        }
//    }


    @SerializedName("DealType")
    private String detailType;

    @SerializedName("IsPay")
    private String isPay;

    @SerializedName("Money")
    private String money;

    @SerializedName("OrderCode")
    private String orderCode;

    @SerializedName("PaymentMode")
    private String paymentMode;

    @SerializedName("Time")
    private String time;

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
