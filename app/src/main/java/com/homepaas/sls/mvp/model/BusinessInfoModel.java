package com.homepaas.sls.mvp.model;

import android.text.TextUtils;

import com.homepaas.sls.domain.entity.ActionEntity;
import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.ActivityNgInfo;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;

import java.util.List;

/**
 * on 2016/1/14 0014
 *
 * @author zhudongjie .
 */
public class BusinessInfoModel implements IServiceInfo {

    private boolean showPhoto;

    private String id;

    private String photo;

    private String photoCount;

    private String phoneNumber;

    private boolean callable;

    private boolean placeOrderEnable;

    private String name;

    private String distance;

    private String property;

    private String establishedTime;

    private String mainBusiness;

    private String area;

    private String scale;

    private String staffNumber;

    private String texId;

    private String licenseId;

    private String address;

    private String serviceTime;

    private String serviceScope;

    private String intro;

    private int likeCount;

    private boolean like;

    private int collectedCount;

    private boolean collected;

    private String orderCount;

    private CharSequence orderRank;

    private String phoneCount;

    private CharSequence phoneRank;

    private String grade;

    private String gradeNumber;

    private CharSequence gradeRank;

    private List<SystemCertification> systemCertifications;

    private double latitude;

    private double longitude;

    private String signature;

    private String number;

    private String defaultService;

    private String defaultServiceId;

    private ActionEntity actionEntity;

    private String isStorePay;  //是否有到店支付
    private String storePayStr;  //到店支付文案

    private List<Photo> licencePhotos;
    private List<String> services;
    private GetBusinessTagsInfo getBusinessTagsInfo;
    private ActivityNgInfoNew activityNgInfos;
    /**
     * 接单数
     */

    private String orderCount2;
    /**
     *被通话次数
     */

    private String phoneCount2;

    /**
     *评分
     */

    private String grade2;

    public String getIsStorePay() {
        return isStorePay;
    }

    public void setIsStorePay(String isStorePay) {
        this.isStorePay = isStorePay;
    }

    public String getStorePayStr() {
        return storePayStr;
    }

    public void setStorePayStr(String storePayStr) {
        this.storePayStr = storePayStr;
    }

    public String getOrderCount2() {
        return orderCount2;
    }

    public void setOrderCount2(String orderCount2) {
        this.orderCount2 = orderCount2;
    }

    public String getPhoneCount2() {
        return phoneCount2;
    }

    public void setPhoneCount2(String phoneCount2) {
        this.phoneCount2 = phoneCount2;
    }

    public String getGrade2() {
        return grade2;
    }

    public void setGrade2(String grade2) {
        this.grade2 = grade2;
    }


    public ActionEntity getActionEntity() {
        return actionEntity;
    }

    public void setActionEntity(ActionEntity actionEntity) {
        this.actionEntity = actionEntity;
    }

    public List<Photo> getLicencePhotos() {
        return licencePhotos;
    }

    public void setLicencePhotos(List<Photo> licencePhotos) {
        this.licencePhotos = licencePhotos;
    }

    public GetBusinessTagsInfo getGetBusinessTagsInfo() {
        return getBusinessTagsInfo;
    }

    public void setGetBusinessTagsInfo(GetBusinessTagsInfo getBusinessTagsInfo) {
        this.getBusinessTagsInfo = getBusinessTagsInfo;
    }

    public ActivityNgInfoNew getActivityNgInfos() {
        return activityNgInfos;
    }

    public void setActivityNgInfos(ActivityNgInfoNew activityNgInfos) {
        this.activityNgInfos = activityNgInfos;
    }

    public BusinessInfoModel(String id) {
        this.id = id;
    }

    public boolean isShowPhoto() {
        return showPhoto;
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

    public String getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(String photoCount) {
        this.photoCount = photoCount;
    }

    public boolean noBusinessMessage(){
        if (TextUtils.isEmpty(getEstablishedTime()) && TextUtils.isEmpty(getProperty()) && TextUtils.isEmpty(getArea())
                && TextUtils.isEmpty(getScale()) && TextUtils.isEmpty(getStaffNumber()) && TextUtils.isEmpty(getIntro()) && TextUtils.isEmpty(getSignature()))
            return true;
        else
            return false;
    }
    @Override
    public int type() {
        return TYPE_BUSINESS;
    }

    @Override
    public boolean showPhoto() {
        return showPhoto;
    }

    @Override
    public void setShowPhoto(boolean showPhoto) {
        this.showPhoto = showPhoto;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public int getLikeCount() {
        return likeCount;
    }

    @Override
    public boolean isLike() {
        return like;
    }

    public String getDefaultService() {
        return defaultService;
    }

    public void setDefaultService(String defaultService) {
        this.defaultService = defaultService;
    }

    public String getDefaultServiceId() {
        return defaultServiceId;
    }


    public void setDefaultServiceId(String defaultServiceId) {
        this.defaultServiceId = defaultServiceId;
    }

    public List<String> getServices() {
        return services;
    }

    @Override
    public String getSplicedServices() {
        if (services != null && !services.isEmpty()) {
            StringBuilder sb = new StringBuilder(" ");
            for (String string : services) {
                sb.append(string).append(" ");
            }
            return sb.toString();
        } else {
            return "";
        }

    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    @Override
    public String getOrderCount() {
        return orderCount;
    }

    @Override
    public CharSequence getOrderRank() {
        return orderRank;
    }

    @Override
    public String getPhoneCount() {
        return phoneCount;
    }

    @Override
    public int getCollectedCount() {
        return collectedCount;
    }

    @Override
    public boolean isCollected() {
        return collected;
    }

    @Override
    public List<SystemCertification> getSystemCertifications() {
        return systemCertifications;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public CharSequence getPhoneRank() {
        return phoneRank;
    }

    public void setPhoneRank(CharSequence phoneRank) {
        this.phoneRank = phoneRank;
    }

    public String getArea() {
        return area;
    }

    public String getEstablishedTime() {
        return establishedTime;
    }

    public String getAddress() {
        return address;
    }

    public String getDistance() {
        return distance;
    }

    public String getProperty() {
        return property;
    }

    public String getMainBusiness() {
        return mainBusiness;
    }

    public String getScale() {
        return scale;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public String getTexId() {
        return texId;
    }

    public String getLicenseId() {
        return licenseId;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public String getServiceScope() {
        return serviceScope;
    }

    public String getIntro() {
        return intro;
    }

    public String getGrade() {
        return grade;
    }

    public String getGradeNumber() {
        return gradeNumber;
    }

    public void setGradeNumber(String gradeNumber) {
        this.gradeNumber = gradeNumber;
    }

    public CharSequence getGradeRank() {
        return gradeRank;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setEstablishedTime(String establishedTime) {
        this.establishedTime = establishedTime;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public void setTexId(String texId) {
        this.texId = texId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setServiceScope(String serviceScope) {
        this.serviceScope = serviceScope;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public void setCollectedCount(int collectedCount) {
        this.collectedCount = collectedCount;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public void setOrderRank(CharSequence orderRank) {
        this.orderRank = orderRank;
    }

    public void setPhoneCount(String phoneCount) {
        this.phoneCount = phoneCount;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setGradeRank(CharSequence gradeRank) {
        this.gradeRank = gradeRank;
    }

    public void setSystemCertifications(List<SystemCertification> systemCertifications) {
        this.systemCertifications = systemCertifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessInfoModel infoModel = (BusinessInfoModel) o;

        return id.equals(infoModel.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public boolean isCallable() {
        return callable;
    }

    public void setCallable(boolean callable) {
        this.callable = callable;
    }

    public boolean isPlaceOrderEnable() {
        return placeOrderEnable;
    }

    public void setPlaceOrderEnable(boolean placeOrderEnable) {
        this.placeOrderEnable = placeOrderEnable;
    }
}
