package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sherily on 2016/6/20.
 * 优惠券（Body）数据模型
 */
public class CouponContents implements Serializable{

    @SerializedName("Start")
    private String start;
    @SerializedName("Id")
    private String id;
    @SerializedName("Title")
    private String title;
    @SerializedName("UserId")
    private String userId;
    @SerializedName("IsUsed")
    private String isUsed;
    @SerializedName("CreateTime")
    private String createTime;
    @SerializedName("CouponDetailses")
    private List<CouponDetails> couponDetailses;
    @SerializedName("PlatFormString")
    private String PlatFormString;
    @SerializedName("StartTime")
    private String StartTime;
    @SerializedName("EndTime")
    private String EndTime;
    @SerializedName("MyServiceTypes")
    private List<MyServiceType> myServiceTypes;
    @SerializedName("CouponType")
    private String couponType;
    @SerializedName("Seq")
    private String seq;
    @SerializedName("ServiceItem")
    private ServiceItem serviceItem;
    @SerializedName("Tag")
    private String tag;//排序需要的字段，不参与解析

    @SerializedName("AppViewId")
    private String appViewId;

    public String getAppViewId() {
        return appViewId;
    }

    public void setAppViewId(String appViewId) {
        this.appViewId = appViewId;
    }

    public boolean isUsed(){
        return "1".equals(isUsed);
    }
    public String getTag() {
        return tag;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(ServiceItem serviceItem) {
        this.serviceItem = serviceItem;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<MyServiceType> getMyServiceTypes() {
        return myServiceTypes;
    }

    public void setMyServiceTypes(List<MyServiceType> myServiceTypes) {
        this.myServiceTypes = myServiceTypes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public List<CouponDetails> getCouponDetailses() {
        return couponDetailses;
    }

    public void setCouponDetailses(List<CouponDetails> couponDetailses) {
        this.couponDetailses = couponDetailses;
    }


    public String getPlatFormString() {
        return PlatFormString;
    }

    public void setPlatFormString(String platFormString) {
        PlatFormString = platFormString;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public static class CouponDetails implements Serializable{
        private String amount;
        private String discountAmount;
        private String discount;
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

    public static class MyServiceType implements Serializable{
        private String serviceId;
        private String serviceName;
        private String typeId;
        private String typeLogo;
        private String TypeName;
        private ServicePrice servicePrice;
        private List<MyServiceType> Children;

        public void setTypeLogo(String typeLogo) {
            this.typeLogo = typeLogo;
        }

        public void setTypeName(String typeName) {
            TypeName = typeName;
        }

        public void setServicePrice(ServicePrice servicePrice) {
            this.servicePrice = servicePrice;
        }

        public void setChildren(List<MyServiceType> children) {
            Children = children;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

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
    public static class ServicePrice implements Serializable{
        private String min;
        private String max;
        private String unit;
        private String UnitName;
        private String IsNegotiable;
        private String PriceList;
        private String StartingPrice;
        private String ServiceTypeId;

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getUnitName() {
            return UnitName;
        }

        public void setUnitName(String unitName) {
            UnitName = unitName;
        }

        public String getIsNegotiable() {
            return IsNegotiable;
        }

        public void setIsNegotiable(String isNegotiable) {
            IsNegotiable = isNegotiable;
        }

        public String getPriceList() {
            return PriceList;
        }

        public void setPriceList(String priceList) {
            PriceList = priceList;
        }

        public String getStartingPrice() {
            return StartingPrice;
        }

        public void setStartingPrice(String startingPrice) {
            StartingPrice = startingPrice;
        }

        public String getServiceTypeId() {
            return ServiceTypeId;
        }

        public void setServiceTypeId(String serviceTypeId) {
            ServiceTypeId = serviceTypeId;
        }
    }
//    public List<MyServiceType> getMyServiceType() {
//        return myServiceTypes;
//    }

//    public void setServiceTypes(List<MyServiceType> myServiceTypes) {
//        this.myServiceTypes = myServiceTypes;
//    }
}
