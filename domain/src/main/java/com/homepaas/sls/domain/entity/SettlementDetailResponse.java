package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by JWC on 2016/12/5.
 */

public class SettlementDetailResponse implements Serializable {
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
    @SerializedName("FreezingPeriodDays")
    private String freezingPeriodDays;

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

    public String getFreezingPeriodDays() {
        return freezingPeriodDays;
    }

    public void setFreezingPeriodDays(String freezingPeriodDays) {
        this.freezingPeriodDays = freezingPeriodDays;
    }

}
