package com.homepaas.sls.mvp.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.ActionEntity;
import com.homepaas.sls.domain.entity.ServiceType;

import java.util.List;

/**
 * Created by Administrator on 2016/8/28.
 */
public class WorkerBussinesModel {
    private String id;
    private String photo;
    private String phoneNumber;
    private String acceptOrder;
    private String name;
    private String gender;
    private String age;
    private String distance;
    private String nativePlace;
    private String praiseCount;
    private String favoriteCount;
    private String grade;
    private String latitude;
    private String longitude;
    private String displayAttribute;
    private ServiceType defaultService;
    private List<String> services;
    private ActionEntity actionEntity;
    private String isWholeSB;
    private boolean isWhole;
    private int type;//0:WORKER;1:BUSSINESS

    public String getIsWholeSB() {
        return isWholeSB;
    }

    public void setIsWholeSB(String isWholeSB) {
        this.isWholeSB = isWholeSB;
    }

    public boolean isWhole() {
        if (!TextUtils.isEmpty(isWholeSB))
        {
            isWhole = isWholeSB.equals("1");
        } else {
            isWhole = false;
        }
        return isWhole;
    }

    public void setWhole(boolean whole) {
        isWhole = whole;
    }

    public ActionEntity getActionEntity() {
        return actionEntity;
    }

    public void setActionEntity(ActionEntity actionEntity) {
        this.actionEntity = actionEntity;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public WorkerBussinesModel() {
    }

    public String getId() {
        return id;
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

    public String getAcceptOrder() {
        return acceptOrder;
    }

    public void setAcceptOrder(String acceptOrder) {
        this.acceptOrder = acceptOrder;
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
