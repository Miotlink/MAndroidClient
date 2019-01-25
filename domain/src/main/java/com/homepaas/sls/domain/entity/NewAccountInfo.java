package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/5.
 */

public class NewAccountInfo {
    @SerializedName("UserId")
    String userId;
    @SerializedName("SettlementBalance")
    String settlementBalance;

    @SerializedName("Activities")
    Activity activity;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSettlementBalance() {
        return settlementBalance;
    }

    public void setSettlementBalance(String settlementBalance) {
        this.settlementBalance = settlementBalance;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public static class Activity implements Serializable {
        @SerializedName("ActivityId")
        String activityId;
        @SerializedName("ReturnType")
        String returnType;
        @SerializedName("Content")
        String content;

        public String getActivityId() {
            return activityId;
        }

        public void setActivityId(String activityId) {
            this.activityId = activityId;
        }

        public String getReturnType() {
            return returnType;
        }

        public void setReturnType(String returnType) {
            this.returnType = returnType;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
