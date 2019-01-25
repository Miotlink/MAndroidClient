package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JWC on 2017/2/22.
 */

public class GetAtStoreActivityEntity {
    @SerializedName("Details")
    List<ActivityNgDetail> activityNgDetailList;

    public List<ActivityNgDetail> getActivityNgDetailList() {
        return activityNgDetailList;
    }

    public void setActivityNgDetailList(List<ActivityNgDetail> activityNgDetailList) {
        this.activityNgDetailList = activityNgDetailList;
    }

    public static class ActivityNgDetail {
        /**
         * 返还方式，减免 = 0, 返充 = 1,红包 = 2,充值折扣 = 3,
         * 充值送余额 = 4,充值送红包= 5,App引流红包=6
         */
        @SerializedName("ReturnType")
        private String returnType;
        /**
         * 可能的值如：特价小时工，特价送花
         */
        @SerializedName("Title")
        private String title;
        /**
         * 如：满90减10，满80减5
         */
        @SerializedName("Help")
        private String help;
        @SerializedName("Rules")
        private List<ActivityNgOfRule> activityNgOfRuleList;

        /**
         * 在活动删选中记录该活动中满足条件的最优规则的Index
         */
        private int bestRuleIndex = -1;
        /**
         * 是否满足减免或者减返活动
         */
        private boolean satified=false;



        public boolean isSatified() {
            return satified;
        }

        public void setSatified(boolean satified) {
            this.satified = satified;
        }

        public String getReturnType() {
            return returnType;
        }

        public void setReturnType(String returnType) {
            this.returnType = returnType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHelp() {
            return help;
        }

        public void setHelp(String help) {
            this.help = help;
        }

        public List<ActivityNgOfRule> getActivityNgOfRuleList() {
            return activityNgOfRuleList;
        }

        public void setActivityNgOfRuleList(List<ActivityNgOfRule> activityNgOfRuleList) {
            this.activityNgOfRuleList = activityNgOfRuleList;
        }

        public int getBestRuleIndex() {
            return bestRuleIndex;
        }

        public void setBestRuleIndex(int bestRuleIndex) {
            this.bestRuleIndex = bestRuleIndex;
        }

    }

    public static class ActivityNgOfRule {
        @SerializedName("AcitivityNgId")
        private String acitivityNgId;
        //        满多少
        @SerializedName("Upper")
        private String upper;
        //        减多少
        @SerializedName("Minus")
        private String minus;

        public String getAcitivityNgId() {
            return acitivityNgId;
        }

        public void setAcitivityNgId(String acitivityNgId) {
            this.acitivityNgId = acitivityNgId;
        }

        public String getUpper() {
            return upper;
        }

        public void setUpper(String upper) {
            this.upper = upper;
        }

        public String getMinus() {
            return minus;
        }

        public void setMinus(String minus) {
            this.minus = minus;
        }
    }

    /**
     * 获取最优的减免或者减返
     */

    public  List<ActivityNgDetail> getBestActivity(float money){
        List<ActivityNgDetail> satisfiedActivityNgDetailList = new ArrayList<>();
        if(activityNgDetailList!=null){
            for(ActivityNgDetail activityNgDetail:activityNgDetailList){
                if (activityNgDetail != null && activityNgDetail.getActivityNgOfRuleList()!= null &&!activityNgDetail.getActivityNgOfRuleList().isEmpty()){
                    float maxUpper = 0.0f;
                    int bestRuleIndex = -1;
                    //先删选每个活动中满足条件的最优规则并做记录
                    for (ActivityNgOfRule activityNgOfRule : activityNgDetail.getActivityNgOfRuleList()){
                        if (money >= Float.parseFloat(activityNgOfRule.getUpper()) && Float.parseFloat(activityNgOfRule.getUpper()) >= maxUpper){
                            maxUpper = Float.parseFloat(activityNgOfRule.getUpper());
                            bestRuleIndex = activityNgDetail.getActivityNgOfRuleList().indexOf(activityNgOfRule);
                        }
                    }
                    //记录下满足条件的活动，并存入新的活动集合中
                    if (bestRuleIndex >= 0){
                        activityNgDetail.setBestRuleIndex(bestRuleIndex);
                        activityNgDetail.setSatified(true);
                        satisfiedActivityNgDetailList.add(activityNgDetail);
                    } else {
                        activityNgDetail.setBestRuleIndex(-1);
                        activityNgDetail.setSatified(false);
                    }
                }
            }
        }
        return satisfiedActivityNgDetailList;
    }
}


