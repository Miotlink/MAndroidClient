package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CJJ on 2016/9/8.
 */
public class BusinessEntity implements IService, Serializable {
    @SerializedName("Id")
    public String id;
    @SerializedName("Photo")
    public String photo;
    @SerializedName("PhoneNumber")
    public String phoneNumber;
    @SerializedName("IsCall")//是否可以拨打电话	1:可以;0：不可
    public String isCall;
    @SerializedName("AcceptOrder")
    public String acceptOrder; //1接单，其他：暂停接单
    @SerializedName("Name")
    public String name;
    @SerializedName("Distance")
    public String distance;
    @SerializedName("Address")
    public String address;
    @SerializedName("PraiseCount")
    public String praiseCount;
    @SerializedName("FavoriteCount")
    public String favoriteCount;
    @SerializedName("Grade")
    public String grade;
    @SerializedName("Latitude")
    public String latitude;
    @SerializedName("Longitude")
    public String longitude;
    @SerializedName("DefaultService")
    public ServiceType defaultService;
    @SerializedName("Services")
    public List<String> services;
    @SerializedName("IsWholeMerchant")
    private String isWholeMerchant;
    /**
     * 时间
     */
    @SerializedName("ServiceTime")
    private String serviceTime;

    /**
     * 是否活动减免	1：是，0：否
     */
    @SerializedName("IsReduction")
    private String isReduction;
    /**
     * 是否活动返现	1：是，0：否
     */
    @SerializedName("IsReturn")
    private String isReturn;
    /**
     * 是否有红包	1：是，0：否
     */
    @SerializedName("IsMoneyRed")
    private String isMoneyRed;
    @SerializedName("Activity")
    private ActionEntity actionEntity;

    public ActionEntity getActionEntity() {
        return actionEntity;
    }

    public void setActionEntity(ActionEntity actionEntity) {
        this.actionEntity = actionEntity;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getIsCall() {
        return isCall;
    }

    public void setIsCall(String isCall) {
        this.isCall = isCall;
    }

    public String getIsReduction() {
        return isReduction;
    }

    public void setIsReduction(String isReduction) {
        this.isReduction = isReduction;
    }

    public String getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(String isReturn) {
        this.isReturn = isReturn;
    }

    public String getIsMoneyRed() {
        return isMoneyRed;
    }

    public void setIsMoneyRed(String isMoneyRed) {
        this.isMoneyRed = isMoneyRed;
    }

    @Override
    public boolean isWholeCityProvider() {
        return "1".equals(isWholeMerchant);
    }

    @Override
    public boolean isCommonBusinessWorker() {
        return false;
    }

    public String getIsWholeMerchant() {
        return isWholeMerchant;
    }

    public void setIsWholeMerchant(String isWholeMerchant) {
        this.isWholeMerchant = isWholeMerchant;
    }

    public String getAcceptOrder() {
        return acceptOrder;
    }

    public void setAcceptOrder(String acceptOrder) {
        this.acceptOrder = acceptOrder;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(String praiseCount) {
        this.praiseCount = praiseCount;
    }

    public String getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(String favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public ServiceType getDefaultService() {
        return defaultService;
    }

    public void setDefaultService(ServiceType defaultService) {
        this.defaultService = defaultService;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    @Override
    public boolean isWorker() {
        return false;
    }

    @Override
    public boolean isBusiness() {
        return true;
    }

    @Override
    public double getLng() {
        return Double.parseDouble(longitude);
    }

    @Override
    public double getLat() {
        return Double.parseDouble(latitude);
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getIcon() {
        return defaultService.iconNormal;
    }

    @Override
    public String getIconChecked() {
        return defaultService.iconChecked;
    }

    @Override
    public ServiceType getDefService() {
        return defaultService;
    }

    @Override
    public String getPhotoUrl() {
        return photo;
    }


    @Override
    public int getServiceType() {
        return 2;
    }

    @Override
    public List<String> getServiceList() {
        return services;
    }

}
