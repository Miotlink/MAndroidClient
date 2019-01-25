package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CJJ on 2016/9/8.
 *
 */
public class WorkerEntity implements IService,Serializable {

    @SerializedName("Id")
    public String id;
    @SerializedName("Photo")
    public String photo;
    @SerializedName("PhoneNumber")
    public String phoneNumber;
    /**
     * 是否可以拨打电话	1:可;0:不可
     */
    @SerializedName("IsCall")
    private String iscall;
    /**
     * 1：接单
     *其它：暂停接单
     */
    @SerializedName("AcceptOrder")
    public String acceptOrder;
    @SerializedName("Name")
    public String name;
    @SerializedName("Gender")
    public String gender;
    @SerializedName("Age")
    public String age;
    @SerializedName("Distance")
    public String distance;
    @SerializedName("NativePlace")
    public String nativePlace;
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
    @SerializedName("DisplayAttribute")
    public String displayAttribute;
    @SerializedName("DefaultService")
    public ServiceType defaultService;
    @SerializedName("Services")
    public List<String> services;


    /**
     * 是否全城工人
     * 1：是，0：否
     *这个字段在这一版本中有用，未来版本可能没用了
     */
    @SerializedName("IsWholeWorker")
    private String isWholeWorker;
    /**
     * 时薪
     */
    @SerializedName("Wage")
    private String wage;

    /**
     * 活动信息
     * 当前与该工人有关的活动详情
     */
    @SerializedName("Activity")
    private ActionEntity actionEntity;
//
//    public boolean isWholeWorker(){
//        return isWholeWorker.equals("1");
//    }

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

    @SerializedName("IsBusinessWorker")
    private String BusinessWorker;

    @SerializedName("ServicePrice")
    private String servicePrice;

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getBusinessWorker() {
        return BusinessWorker;
    }

    public void setBusinessWorker(String businessWorker) {
        BusinessWorker = businessWorker;
    }

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
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

    public String getIscall() {
        return iscall;
    }

    public void setIscall(String iscall) {
        this.iscall = iscall;
    }

    public String getIsWholeWorker() {
        return isWholeWorker;
    }

    public void setIsWholeWorker(String isWholeWorker) {
        this.isWholeWorker = isWholeWorker;
    }

    public ActionEntity getActionEntity() {
        return actionEntity;
    }

    public void setActionEntity(ActionEntity actionEntity) {
        this.actionEntity = actionEntity;
    }

    public String getAcceptOrder() {
        return acceptOrder;
    }

    public void setAcceptOrder(String acceptOrder) {
        this.acceptOrder = acceptOrder;
    }

    public String getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(String praiseCount) {
        this.praiseCount = praiseCount;
    }

    @Override
    public boolean isWorker() {
        return true;
    }

    @Override
    public boolean isBusiness() {
        return false;
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
    public boolean isWholeCityProvider() {
        return "1".equals(isWholeWorker);
    }

    @Override
    public boolean isCommonBusinessWorker() {
        if(BusinessWorker!=null) {
         return BusinessWorker.equals("1")?true:false;
        }
        return false;
    }

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
        return 1;
    }

    @Override
    public List<String> getServiceList() {
        return services;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getPaiseCount() {
        return praiseCount;
    }

    public void setPaiseCount(String praiseCount) {
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

    public String getDisplayAttribute() {
        return displayAttribute;
    }

    public void setDisplayAttribute(String displayAttribute) {
        this.displayAttribute = displayAttribute;
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
}
