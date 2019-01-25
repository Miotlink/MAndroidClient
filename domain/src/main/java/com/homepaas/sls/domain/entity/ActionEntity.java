package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sherily on 2016/9/16.
 */
public class ActionEntity implements Serializable {
    @SerializedName("ServiceTypeId")
    private String serviceTypeId;

    @SerializedName("Slogan")

    private String slogan;

    @SerializedName("SpecialRule")
    private List<ActRule> specialRule;

    @SerializedName("PromotionRule")
    private List<ActRule> promotionRule;


    //特价名称:"特价小时工,快递,送水等"
    @SerializedName("SpecialTitle")
    private String specialTitle;

    //特价备注	点击“?”出来的内容
    @SerializedName("SpecialHelp")
    private String specialHelp;

    //特价不满足活动条件时的说明
    @SerializedName("SpecialAds")




    private String specialAds;

    //优惠名称	满N返M
    @SerializedName("PromotionTitle")
    private String promotionTitle;
    //已享优惠	已享优惠：满N返M
    @SerializedName("PromotionName")
    private String promotionName;

    //优惠备注	点击“?”出来的内容
    @SerializedName("PromotionHelp")
    private String promotionHelp;

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public List<ActRule> getSpecialRule() {
        return specialRule;
    }

    public void setSpecialRule(List<ActRule> specialRule) {
        this.specialRule = specialRule;
    }

    public List<ActRule> getPromotionRule() {
        return promotionRule;
    }

    public void setPromotionRule(List<ActRule> promotionRule) {
        this.promotionRule = promotionRule;
    }

    public String getSpecialTitle() {
        return specialTitle;
    }

    public void setSpecialTitle(String specialTitle) {
        this.specialTitle = specialTitle;
    }

    public String getSpecialHelp() {
        return specialHelp;
    }

    public void setSpecialHelp(String specialHelp) {
        this.specialHelp = specialHelp;
    }

    public String getPromotionTitle() {
        return promotionTitle;
    }

    public void setPromotionTitle(String promotionTitle) {
        this.promotionTitle = promotionTitle;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionHelp() {
        return promotionHelp;
    }

    public void setPromotionHelp(String promotionHelp) {
        this.promotionHelp = promotionHelp;
    }

    public static class ActRule implements Serializable {
        //满多少
        @SerializedName("Upper")
        private String upper;
        //减（或返）多少,数值，不得大于Upper
        @SerializedName("Minus")
        private String minus;

        @SerializedName("Title")
        private String title;

        @SerializedName("ActivityNgId")
        private String activityNgId;

        //减的话是1，返的话是2；
        private String reduce;

        public String getReduce() {
            return reduce;
        }

        public void setReduce(String reduce) {
            this.reduce = reduce;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getActivityNgId() {
            return activityNgId;
        }

        public void setActivityNgId(String activityNgId) {
            this.activityNgId = activityNgId;
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

        public boolean isEmpty() {
            if (upper != null &&
                    minus != null) {
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 是否可以参与特价"满立减"活动,
     * 可以参与的情况下,则显示相关活动信息,不可参与的时候不显示相关活动信息
     */
    public boolean isSpecialAvailable() {
        if (specialRule != null &&
                !specialRule.isEmpty() &&
                !specialRule.get(0).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否满足特价条件
     * @param money 当前订单的金额
     * @return true: 已达到特价条件,可以立减部分金额, false: 不满足,不能立减
     */
    public boolean isSpecialSatisfied(float money) {
        if (!isSpecialAvailable()) {
            return false;
        }

        //目前特价只有一个活动,只取一个
        ActRule actRule = specialRule.get(0);
        float upperMoney = Float.parseFloat(actRule.getUpper());
        if (money >= upperMoney) {
            //当订单金额超过活动需要金额
            return true;
        } else {
            return false;
        }
    }

    /**
     * 当用户可以参与特价活动时,并且没有达到活动条件(金额不满足)时显示该信息,
     * @note: 同时可以认为价格区间的服务都没达到活动条件,所以都显示该信息
     * @return 返回说明文案(PS:在订单金额Cell下面显示该信息, 同时由于没达到活动条件,特价信息Cell不显示)
     */
    public String getSpecialAvailableDesc() {
        return specialAds;
    }

    /**
     * 当用户达到特价活动要求时, 显示相关信息
     * @return 返回说明文案(PS: 显示特价信息Cell, 订单金额Cell也显示优惠信息),
     * 相关优惠信息从{@link #getSpcialSatisfiedInfo()}拿
     */
    public String getSpecialSatisfiedDesc() {
        return specialTitle;
    }

    /**
     * 返回帮助说明
     * @return 帮助说明url
     */
    public String getSpecialHelper() {
        return specialHelp;
    }

    /**
     * 获取特价活动相关优惠信息
     * @return 优惠信息
     */
    public ActRule getSpcialSatisfiedInfo() {
        return specialRule.get(0);
    }

    /**
     * 是否可以参与"满再返"活动,
     * 可以参与的情况下,则显示相关活动信息,不可参与的时候不显示相关活动信息
     * @return true: 可以参加"满再返"活动,显示相关活动信息(PS:只显示相关活动信息)
     */
    public boolean isPromotionAvailable() {
        if (promotionRule != null &&
                !promotionRule.isEmpty() &&
                !promotionRule.get(0).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否已达到"满再返"的条件,通过订单金额判断
     * @param money 订单金额
     * @return true: 已满足, false: 不满足
     */
    public boolean isPromotionSatisfied(float money) {
        if (!isPromotionAvailable()) {
            return false;
        }

        //目前"满再返"只有一个活动
        ActRule actRule = promotionRule.get(0);
        float upperMoney = Float.parseFloat(actRule.getUpper());
        if (money >= upperMoney) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取"满再返"活动说明
     * @return 活动信息
     */
    public String getPromotionAvailableDesc() {
        return promotionTitle;
    }

    /**
     * 获取达到"满再返"的描述
     * @note: 因为"满再返"条件满足是通过金额判断,所以价格区间和面议的不需要显示优惠信息,只需要显示活动信息
     * @return 优惠信息
     */
    public String getPromotionSatisfiedDesc() {
        return promotionName;
    }

    /**
     * 返回帮助说明
     * @return 帮助说明 url
     */
    public String getPromotionSatisfiedHelper() {
        return promotionHelp;
    }

}
