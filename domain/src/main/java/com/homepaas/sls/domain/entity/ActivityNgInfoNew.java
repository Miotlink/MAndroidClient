package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sherily on 2017/2/21.
 */

public class ActivityNgInfoNew implements Serializable {
    /**
     * 按照品类分类的活动，如：小时工（擦玻璃），全品类
     */
    @SerializedName("ServiceTypeRules")
    private List<AcitivityNgOfServiceType> acitivityNgOfServiceTypeList;

    public List<AcitivityNgOfServiceType> getAcitivityNgOfServiceTypeList() {
        return acitivityNgOfServiceTypeList;
    }

    /**
     * 是否有相关活动
     */
    public boolean isAvailableActivity() {
        if (acitivityNgOfServiceTypeList != null && !acitivityNgOfServiceTypeList.isEmpty()) {
            setFullcategoryFlag();
            return true;
        } else {
            return false;
        }
    }

    public void setFullcategoryFlag() {//设置全品类标志位
        for (AcitivityNgOfServiceType acitivityNgOfServiceType : acitivityNgOfServiceTypeList) {
            if (acitivityNgOfServiceType != null && "0".equals(acitivityNgOfServiceType.getServiceTypeId())) {
                for (ActivityNgDetail activityNgDetail : acitivityNgOfServiceType.getActivityNgDetailList()) {
                    if (activityNgDetail != null)
                        activityNgDetail.setFullcategory(true);
                }
            }
        }
    }

    public List<ActivityNgDetail> getNeedActivity(String type) {
        List<ActivityNgDetail> activityNgDetailList = new ArrayList<>();
        if (isAvailableActivity()) {
            for (AcitivityNgOfServiceType acitivityNgOfServiceType : acitivityNgOfServiceTypeList) {
                if (acitivityNgOfServiceType != null) {
                    for (ActivityNgDetail activityNgDetail : acitivityNgOfServiceType.getActivityNgDetailList()) {
                        if (activityNgDetail != null) {
                            /**
                             * 返还方式，减免 = 0, 返充 = 1,红包 = 2,充值折扣 = 3,
                             * 充值送余额 = 4,充值送红包= 5,App引流红包=6
                             */
                            if ("-1".equals(type))
                                activityNgDetailList.add(activityNgDetail);//   返回所有活动
                            else if (type.equals(activityNgDetail.getReturnType()))
                                activityNgDetailList.add(activityNgDetail);

                        }
                    }
                }
            }
        }
        return activityNgDetailList;

    }
    /**
     * 是否可以参与特价"满立减"活动,
     * 可以参与的情况下,则显示相关活动信息,不可参与的时候不显示相关活动信息
     */
    public boolean isSpecialAvailable() {
        if (isAvailableActivity() && !getNeedActivity("0").isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    private List<ActivityNgDetail> satisfiedSpecialActivityList;
    private List<ActivityNgDetail> satisfiedPromotionActivityList;
    private List<ActivityNgDetail> satisifiedRedPacketActivityList;

    public List<ActivityNgDetail> getSatisifiedRedPacketActivityList() {
        return satisifiedRedPacketActivityList;
    }

    //判断 isSpecialSatisfied =true后获取
    public List<ActivityNgDetail> getSatisfiedSpecialActivityList() {
        return satisfiedSpecialActivityList;
    }
    //判断isPromotionSatisfied = true 后获取
    public List<ActivityNgDetail> getSatisfiedPromotionActivityList() {
        return satisfiedPromotionActivityList;
    }

    /**
     * 正常情况下的配置：同一个活动下的规则，upper值不会有重复，minus值也不会有重复，且minus值随着upper值的增加而增加
     * @param money
     * @param type
     * @return
     */
    public List<ActivityNgDetail> getBestActivity(float money, String type) {
//        ActivityNgDetail bestActivity = null;
        List<ActivityNgDetail> satisfiedActivityNgDetailList = new ArrayList<>();
        if (!getNeedActivity(type).isEmpty()) {
            for (ActivityNgDetail activityNgDetail : getNeedActivity(type)) {
                if (activityNgDetail != null && activityNgDetail.getActivityNgOfRuleList() != null && !activityNgDetail.getActivityNgOfRuleList().isEmpty()) {
                    float maxUpper = 0.0f;
                    int bestRuleIndex = -1;
                    //先删选每个活动中满足条件的最优规则并做记录
                    for (ActivityNgOfRule activityNgOfRule : activityNgDetail.getActivityNgOfRuleList()) {
                        if (money >= Float.parseFloat(activityNgOfRule.getUpper()) && Float.parseFloat(activityNgOfRule.getUpper()) >= maxUpper) {
                            maxUpper = Float.parseFloat(activityNgOfRule.getUpper());
                            bestRuleIndex = activityNgDetail.getActivityNgOfRuleList().indexOf(activityNgOfRule);
                        }
                    }
                    //记录下满足条件的活动，并存入新的活动集合中
                    if (bestRuleIndex >= 0) {
                        activityNgDetail.setBestRuleIndex(bestRuleIndex);
                        activityNgDetail.setSatified(true);
                        satisfiedActivityNgDetailList.add(activityNgDetail);
                    } else {
                        activityNgDetail.setBestRuleIndex(-1);
                        activityNgDetail.setSatified(false);
                    }
                }
            }
//            //在满足条件的活动集合中删选出upper值最高的，即最佳活动
//            if (!satisfiedActivityNgDetailList.isEmpty()){
//                bestActivity = satisfiedActivityNgDetailList.get(0);
//                for (ActivityNgDetail activityNgDetail : satisfiedActivityNgDetailList){
//                    float bestActivityRuleUpper = Float.parseFloat(bestActivity.getActivityNgOfRuleList().get(bestActivity.getBestRuleIndex()).getUpper());
//                    float activityRuleUpper = Float.parseFloat(activityNgDetail.getActivityNgOfRuleList().get(activityNgDetail.getBestRuleIndex()).getUpper());
//                    if (activityRuleUpper > bestActivityRuleUpper)
//                        bestActivity = activityNgDetail;
//                }
//            }
        }
//        //根据删选条件记录最佳的满减活动或满返活动（目前只考虑这两种类型）
//            if ("0".equals(type))
//                bestSpecialAvailableActivity = bestActivity;
//            else if ("1".equals(type))
//                bestPromotionAvailableActivity = bestActivity;
        if ("0".equals(type))
            satisfiedSpecialActivityList = satisfiedActivityNgDetailList;
        else if ("1".equals(type))
            satisfiedPromotionActivityList = satisfiedActivityNgDetailList;
        else if ("2".equals(type))
            satisifiedRedPacketActivityList = satisfiedActivityNgDetailList;

        return satisfiedActivityNgDetailList;
    }


    /**
     * 是否满足特价条件
     *
     * @param money 当前订单的金额
     * @return true: 已达到特价条件,可以立减部分金额, false: 不满足,不能立减
     */


    public boolean isSpecialSatisfied(float money) {
        if (!isAvailableActivity()) {
            return false;
        }

//        //目前特价只有一个活动,只取一个
//        ActionEntity.ActRule actRule = specialRule.get(0);
//        float upperMoney = Float.parseFloat(actRule.getUpper());
//        if (money >= upperMoney) {
//            //当订单金额超过活动需要金额
//            return true;
//        } else {
//            return false;
//        }
        if (getBestActivity(money, "0") != null && !getBestActivity(money, "0").isEmpty())
            return true;
        else
            return false;
    }

    /**
     * 是否可以参与"满再返"活动,包括返红包2，返钱1
     * 可以参与的情况下,则显示相关活动信息,不可参与的时候不显示相关活动信息
     *
     * @return true: 可以参加"满再返"活动,显示相关活动信息(PS:只显示相关活动信息)
     */
    public boolean isPromotionAvailable() {
        if (isAvailableActivity() && !getNeedActivity("1").isEmpty() || !getNeedActivity("2").isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否已达到"满再返"的条件,通过订单金额判断
     *
     * @param money 订单金额
     * @return true: 已满足, false: 不满足
     */
    public boolean isPromotionSatisfied(float money) {
        if (!isPromotionAvailable()) {
            return false;
        }

        if ((getBestActivity(money, "1") != null && !getBestActivity(money, "1").isEmpty()) || (getBestActivity(money, "2") != null && !getBestActivity(money, "2").isEmpty()))
            return true;
        else
            return false;
//        //目前"满再返"只有一个活动
//        ActionEntity.ActRule actRule = promotionRule.get(0);
//        float upperMoney = Float.parseFloat(actRule.getUpper());
//        if (money >= upperMoney) {
//            return true;
//        } else {
//            return false;
//        }
    }

    public void setAcitivityNgOfServiceTypeList(List<AcitivityNgOfServiceType> acitivityNgOfServiceTypeList) {
        this.acitivityNgOfServiceTypeList = acitivityNgOfServiceTypeList;
    }

    public static class AcitivityNgOfServiceType {
        @SerializedName("ServiceTypeId")
        private String serviceTypeId;
        @SerializedName("Details")
        private List<ActivityNgDetail> activityNgDetailList;

        public String getServiceTypeId() {
            return serviceTypeId;
        }

        public void setServiceTypeId(String serviceTypeId) {
            this.serviceTypeId = serviceTypeId;
        }

        public List<ActivityNgDetail> getActivityNgDetailList() {
            return activityNgDetailList;
        }

        public void setActivityNgDetailList(List<ActivityNgDetail> activityNgDetailList) {
            this.activityNgDetailList = activityNgDetailList;
        }
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

        /**
         * 在活动删选中记录该活动中满足条件的最优规则的Index
         */
        private int bestRuleIndex = -1;

        private boolean satified = false;

        private boolean isFullcategory = false;

        public boolean isFullcategory() {
            return isFullcategory;
        }

        public void setFullcategory(boolean fullcategory) {
            isFullcategory = fullcategory;
        }

        public boolean isSatified() {
            return satified;
        }

        public void setSatified(boolean satified) {
            this.satified = satified;
        }

        public int getBestRuleIndex() {
            return bestRuleIndex;
        }

        public void setBestRuleIndex(int bestRuleIndex) {
            this.bestRuleIndex = bestRuleIndex;
        }

        @SerializedName("Rules")
        private List<ActivityNgOfRule> activityNgOfRuleList;

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
}
