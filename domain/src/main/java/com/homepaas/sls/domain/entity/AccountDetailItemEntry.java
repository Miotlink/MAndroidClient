package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

public class AccountDetailItemEntry {
    @SerializedName("Id")
    private int id;
    @SerializedName("TradeType")
    private String tradeType;
    @SerializedName("SettlementType")
    private String settlementType;
    @SerializedName("SettlementTime")
    private String settlementTime;
    @SerializedName("SettlementAmount")
    private String settlementAmount;
    @SerializedName("StatusNote")
    private String statusNote;
    @SerializedName("IsMinus")
    private String isMinus;
    @SerializedName("TradeNo")
    private String tradeNo;
    @SerializedName("TradeAmount")
    private String tradeAmount;
    @SerializedName("TradeNote")
    private String tradeNote;
    @SerializedName("BillNumber")
    private String billNumber;
    @SerializedName("PaymentMode")
    private String paymentMode;
    @SerializedName("OrderId")
    private String orderId;
    @SerializedName("DiscountAmount")
    private String discountAmount;
    @SerializedName("IncreaseAmount")
    private String increaseAmount;
    @SerializedName("DecreaseAmount")
    private String decreaseAmount;
    @SerializedName("TradeName")
    private String tradeName;
    @SerializedName("TradeTypeId")
    private String tradeTypeId;

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getIncreaseAmount() {
        return increaseAmount;
    }

    public void setIncreaseAmount(String increaseAmount) {
        this.increaseAmount = increaseAmount;
    }

    public String getDecreaseAmount() {
        return decreaseAmount;
    }

    public void setDecreaseAmount(String decreaseAmount) {
        this.decreaseAmount = decreaseAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }

    public String getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(String settlementTime) {
        this.settlementTime = settlementTime;
    }

    public String getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getStatusNote() {
        return statusNote;
    }

    public void setStatusNote(String statusNote) {
        this.statusNote = statusNote;
    }

    public String getIsMinus() {
        return isMinus;
    }

    public void setIsMinus(String isMinus) {
        this.isMinus = isMinus;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getTradeNote() {
        return tradeNote;
    }

    public void setTradeNote(String tradeNote) {
        this.tradeNote = tradeNote;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getTradeTypeId() {
        return tradeTypeId;
    }

    public void setTradeTypeId(String tradeTypeId) {
        this.tradeTypeId = tradeTypeId;
    }
}
