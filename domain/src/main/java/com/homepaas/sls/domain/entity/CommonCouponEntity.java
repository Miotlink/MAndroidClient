package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.List;

/**
 * Created by JWC on 2017/5/26.
 */

public class CommonCouponEntity {
    @SerializedName("Id")
    private String id;
    //优惠券标题
    @SerializedName("Title")
    private String title;
    //领取的用户
    @SerializedName("UserId")
    private String userId;
    //客户是否已经使用
    @SerializedName("IsUsed")
    private String isUsed;
    //生成时间
    @SerializedName("CreateTime")
    private String createTime;
    //优惠券内容
    @SerializedName("CouponDetails")
    private List<CouponDetails> couponDetailses;
    //可用终端
    @SerializedName("PlatFormString")
    private String platFormString;
    //有效开始时间
    @SerializedName("StartTime")
    private String startTime;
    //有效结束时间
    @SerializedName("EndTime")
    private String endTime;

    @SerializedName("ServiceTypes")
    private List<MyServiceType> myServiceTypes;

    //红包类型
    @SerializedName("CouponType")
    private String couponType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<CouponDetails> getCouponDetailses() {
        return couponDetailses;
    }

    public void setCouponDetailses(List<CouponDetails> couponDetailses) {
        this.couponDetailses = couponDetailses;
    }

    public String getPlatFormString() {
        return platFormString;
    }

    public void setPlatFormString(String platFormString) {
        this.platFormString = platFormString;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<MyServiceType> getMyServiceTypes() {
        return myServiceTypes;
    }

    public void setMyServiceTypes(List<MyServiceType> myServiceTypes) {
        this.myServiceTypes = myServiceTypes;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public static class CouponDetails {
        //满多少 金额
        @SerializedName("Amount")
        private String amount;
        //减多少 金额
        @SerializedName("DiscountAmount")
        private String discountAmount;
        //折扣
        @SerializedName("Discount")
        private String discount;
        //优惠类型
        @SerializedName("DiscountType")
        private String discountType;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(String discountAmount) {
            this.discountAmount = discountAmount;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDiscountType() {
            return discountType;
        }

        public void setDiscountType(String discountType) {
            this.discountType = discountType;
        }
    }

    public static class MyServiceType {
        //服务ID
        @SerializedName("ServiceId")
        private String serviceId;
        //服务名称
        @SerializedName("ServiceName")
        private String serviceName;

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }
    }
}
