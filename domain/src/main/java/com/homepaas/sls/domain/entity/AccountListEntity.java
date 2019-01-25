package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */
public class AccountListEntity {
    @SerializedName("Total")
    private int total;
    @SerializedName("List")
    private List<ClientSettlementDetailResponse> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ClientSettlementDetailResponse> getList() {
        return list;
    }

    public void setList(List<ClientSettlementDetailResponse> list) {
        this.list = list;
    }

    public static class ClientSettlementDetailResponse implements Serializable {
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
        @SerializedName("TradeAmount")
        private String tradeAmount;
        @SerializedName("TradeTypeId")
        private String tradeTypeId;

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

        public String getTradeAmount() {
            return tradeAmount;
        }

        public void setTradeAmount(String tradeAmount) {
            this.tradeAmount = tradeAmount;
        }

        public String getTradeTypeId() {
            return tradeTypeId;
        }

        public void setTradeTypeId(String tradeTypeId) {
            this.tradeTypeId = tradeTypeId;
        }
    }


}
