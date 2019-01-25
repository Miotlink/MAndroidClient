package com.homepaas.sls.data.entity;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.ServiceItem;

import java.util.Collection;
import java.util.List;

/**
 * Created by Sherily on 2016/6/21.
 */
public class CouponContentsEntity {


    @SerializedName("start")
    private String start;

    @SerializedName("Id")
    private String id;

    @SerializedName("Title")
    private String title;

    @SerializedName("CreateTime")
    private String createTime;

    @SerializedName("StartTime")
    private String startTime;

    @SerializedName("EndTime")
    private String endTime;

    @SerializedName("IsUsed")
    private String isUsed;

    @SerializedName("UserId")
    private String userId;

    @SerializedName("CouponDetails")
    private Collection<CouponDetails> couponDetailses;

    @SerializedName("PlatFormString")
    private String platFormString;

    @SerializedName("ServiceTypes")
    private Collection<MyServiceType> myServiceTypes;

    @SerializedName("CouponType")
    private String couponType;

    @SerializedName("Seq")
    private String seq;

    @SerializedName("ServiceItem")
    private ServiceItem serviceItem;


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

    public Collection<CouponDetails> getCouponDetailses() {
        return couponDetailses;
    }

    public void setCouponDetailses(Collection<CouponDetails> couponDetailses) {
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

    public Collection<MyServiceType> getMyServiceTypes() {
        return myServiceTypes;
    }

    public void setMyServiceTypes(Collection<MyServiceType> myServiceTypes) {
        this.myServiceTypes = myServiceTypes;
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

    public static class CouponDetails {
        @SerializedName("Amount")
        private String amount;
        @SerializedName("DiscountAmount")
        private String discountAmount;
        @SerializedName("Discount")
        private String discount;
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
        @SerializedName("ServiceId")
        private String serviceId;
        @SerializedName("ServiceName")
        private String serviceName;
        @SerializedName("TypeId")
        private String typeId;
        @SerializedName("TypeLogo")
        private String typeLogo;
        @SerializedName("TypeName")
        private String TypeName;
        @SerializedName("ServicePrice")
        private ServicePrice servicePrice;
        @SerializedName("Children")
        private List<MyServiceType> Children;

        public String getTypeLogo() {
            return typeLogo;
        }

        public void setTypeLogo(String typeLogo) {
            this.typeLogo = typeLogo;
        }

        public String getTypeName() {
            return TypeName;
        }

        public void setTypeName(String typeName) {
            TypeName = typeName;
        }

        public ServicePrice getServicePrice() {
            return servicePrice;
        }

        public void setServicePrice(ServicePrice servicePrice) {
            this.servicePrice = servicePrice;
        }

        public List<MyServiceType> getChildren() {
            return Children;
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

    public static class ServicePrice{
        @SerializedName("Min")
        private String min;
        @SerializedName("Max")
        private String max;
        @SerializedName("Unit")
        private String unit;
        @SerializedName("UnitName")
        private String UnitName;
        @SerializedName("IsNegotiable")
        private String IsNegotiable;
        @SerializedName("PriceList")
        private String PriceList;
        @SerializedName("StartingPrice")
        private String StartingPrice;
        @SerializedName("ServiceTypeId")
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
}
