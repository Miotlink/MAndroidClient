package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * on 2016/1/13 0013
 *
 * @author zhudongjie .
 */
public class BusinessInfo {


    private String id;

    private String photo;

    private String photoCount;

    private String phoneNumber;

    private String isCall;

    private String allowAcceptOrder;

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

    private String praiseCount;

    private String isPraise;

    private String favoriteCount;

    private String isFavorite;

    private String orderCount;

    private String orderRank;

    private String phoneCount;

    private String phoneRank;

    private String grade;

    private String gradeRank;

    private List<SystemCertification> systemCertifications;

    private String latitude;

    private String longitude;

    private String signature;

    private String number;

    private String defaultService;

    private String defaultServiceId;

    private ActionEntity actionEntity;
    private String isStorePay;  //是否有到店支付
    private String storePayStr;  //到店支付文案

    public ActionEntity getActionEntity() {
        return actionEntity;
    }

    public void setActionEntity(ActionEntity actionEntity) {
        this.actionEntity = actionEntity;
    }

    private List<String> services;
    private GetBusinessTagsInfo getBusinessTagsInfo;
    private ActivityNgInfoNew activityNgInfos;
    private List<Photo> licencePhotos;

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

    private String orderCount2;

    private String phoneCount2;

    private String grade2;

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

    public void setId(String id) {
        this.id = id;
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

    public void setPraiseCount(String praiseCount) {
        this.praiseCount = praiseCount;
    }

    public void setIsPraise(String isPraise) {
        this.isPraise = isPraise;
    }

    public void setFavoriteCount(String favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public void setOrderRank(String orderRank) {
        this.orderRank = orderRank;
    }

    public void setPhoneCount(String phoneCount) {
        this.phoneCount = phoneCount;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setGradeRank(String gradeRank) {
        this.gradeRank = gradeRank;
    }

    public void setSystemCertifications(List<SystemCertification> systemCertifications) {
        this.systemCertifications = systemCertifications;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhoneRank() {
        return phoneRank;
    }

    public void setPhoneRank(String phoneRank) {
        this.phoneRank = phoneRank;
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

    public String getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
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

    public String getEstablishedTime() {
        return establishedTime;
    }

    public String getMainBusiness() {
        return mainBusiness;
    }

    public String getArea() {
        return area;
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

    public String getPraiseCount() {
        return praiseCount;
    }

    public String getIsPraise() {
        return isPraise;
    }

    public String getFavoriteCount() {
        return favoriteCount;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public String getOrderRank() {
        return orderRank;
    }

    public String getPhoneCount() {
        return phoneCount;
    }

    public String getGrade() {
        return grade;
    }

    public String getGradeRank() {
        return gradeRank;
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

    public void setServices(List<String> services) {
        this.services = services;
    }

    public List<SystemCertification> getSystemCertifications() {
        return systemCertifications;
    }

    public String getIsCall() {
        return isCall;
    }

    public void setIsCall(String isCall) {
        this.isCall = isCall;
    }

    public String getAllowAcceptOrder() {
        return allowAcceptOrder;
    }

    public void setAllowAcceptOrder(String allowAcceptOrder) {
        this.allowAcceptOrder = allowAcceptOrder;
    }

    public static class Photo {

        private String smallPic;

        private String hqPic;

        public void setSmallPic(String smallPic) {
            this.smallPic = smallPic;
        }

        public void setHqPic(String hqPic) {
            this.hqPic = hqPic;
        }

        public String getSmallPic() {
            return smallPic;
        }

        public String getHqPic() {
            return hqPic;
        }
    }

    public static class SystemCertification {

        private String name;

        private String description;

        private String image;

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getImage() {
            return image;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessInfo that = (BusinessInfo) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
