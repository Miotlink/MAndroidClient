package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/7.
 */

public class RechargeInfoEntry {
    @SerializedName("response")
    private RechargeInfoResponse response;

    public RechargeInfoResponse getResponse() {
        return response;
    }

    public void setResponse(RechargeInfoResponse response) {
        this.response = response;
    }

    public static class RechargeInfoResponse implements Serializable {
        @SerializedName("RealPaidMoney")
        private String realPaidMoney;
        @SerializedName("TotalMoney")
        private String totalMoney;
        @SerializedName("Activity")
        private String activity;
        @SerializedName("RechargedTime")
        private String rechargedTime;
        @SerializedName("TradeNo")
        private String tradeNo;

        public String getRealPaidMoney() {
            return realPaidMoney;
        }

        public void setRealPaidMoney(String realPaidMoney) {
            this.realPaidMoney = realPaidMoney;
        }

        public String getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(String totalMoney) {
            this.totalMoney = totalMoney;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public String getRechargedTime() {
            return rechargedTime;
        }

        public void setRechargedTime(String rechargedTime) {
            this.rechargedTime = rechargedTime;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }
    }
}
