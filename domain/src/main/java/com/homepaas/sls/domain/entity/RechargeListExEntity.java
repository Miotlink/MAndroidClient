package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/7.
 */

public class RechargeListExEntity {
    @SerializedName("RechargeDescription")
    private String rechargeDescription;
    @SerializedName("List")
    private List<RechargeItem> list;

    public String getRechargeDescription() {
        return rechargeDescription;
    }

    public void setRechargeDescription(String rechargeDescription) {
        this.rechargeDescription = rechargeDescription;
    }

    public List<RechargeItem> getList() {
        return list;
    }

    public void setList(List<RechargeItem> list) {
        this.list = list;
    }

    public static class RechargeItem implements Serializable {
        @SerializedName("TotalMoney")
        private String totalMoney;
        @SerializedName("NeedPay")
        private String needPay;
        @SerializedName("ActivityId")
        private String activityId;
        @SerializedName("Activity")
        private String activity;
        @SerializedName("RechargeMoney")
        private String rechargeMoney;
        @SerializedName("ReturnMoney")
        private String returnMoney;

        //3:折扣 4:充值送钱 5:红包
        @SerializedName("ReturnType")
        private String returnType;

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

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }

        public String getRechargeMoney() {
            return rechargeMoney;
        }

        public void setRechargeMoney(String rechargeMoney) {
            this.rechargeMoney = rechargeMoney;
        }

        public String getReturnMoney() {
            return returnMoney;
        }

        public void setReturnMoney(String returnMoney) {
            this.returnMoney = returnMoney;
        }

        public String getReturnType() {
            return returnType;
        }

        public void setReturnType(String returnType) {
            this.returnType = returnType;
        }
    }
}
