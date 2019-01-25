package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * on 2016/1/12 0012
 *
 * @author zhudongjie .
 */
public class WorkerInfo {


    /**
     * 工人ID
     */
    private String id;

    /**
     * 默认头像
     */
    private String photo;

    /**
     * 头像数量
     */
    private String photoCount;


    /**
     * 电话号码
     */
    private String phoneNumber;


    /**
     * 是否接单	1：接单
     *其它：暂停接单
     */
    private String allowAcceptOrder;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别 "0":male "1":female
     */
    private String gender;

    /**
     * 年龄
     */
    private String age;

    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 时薪
     */
    private String wage;

    /**
     * 距离
     */
    private String distance;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 工作年限
     */
    private String workingYears;

    /**
     * 学历
     */
    private String education;

    /**
     * 爱好
     */
    private String hobby;

    /**
     * 身高
     */
    private String stature;

    /**
     * 体重
     */
    private String weight;

    /**
     * blood type
     */
    private String bloodType;

    /**
     * 星座
     */
    private String constellation;

    /**
     * signature
     */
    private String signature;

    /**
     * 地址
     */
    private String address;

    /**
     * 服务时间 eg: 8:00-20:00
     */
    private String serviceTime;

    /**
     * 服务范围
     */
    private String serviceScope;

    /**
     * 系统介绍
     */
    private String intro;

    /**
     * 点赞 数量
     */
    private String praiseCount;

    /**
     * 用户是否点赞:0:否;1:是
     */
    private String isPraise;

    /**
     * 收藏 数量
     */
    private String favoriteCount;

    /**
     * 是否收藏
     */
    private String isFavorite;

    /**
     * 接单 数量
     */
    private String orderCount;

    /**
     * 接单排名
     */
    private String orderRank;

    /**
     * 被呼叫次
     */
    private String phoneCount;

    /**
     * 被通话排名
     */
    private String phoneRank;

    /**
     * 评分
     */
    private String grade;

    /**
     * 评分排名
     */
    private String gradeRank;

    /**
     * 认证集合
     */
    private List<WorkerSystemCertification> systemCertifications;

    /**
     * 普通话水平
     */
    private String mandarinAbility;

    /**
     *工作经历
     */
    private String workExperience;

    /**
     * 纬度坐标
     */
    private String latitude;

    /**
     * 经度坐标
     */
    private String longitude;




//
//    /**
//     * 头像集合
//     */
//    private List<WorkerPhoto> photos;


    /**
     * 默认服务
     */
    private String defaultService;

    private String defaultServiceId;

    /**
     * 所有服务名集合
     */
    private List<String> services;

    /**
     *活动信息	当前工人参与的活动信息，见活动信息定义
     */
    private ActionEntity actionEntity;
    /**
     * callable
     */
    private String isCall;
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

    private GetWorkerTagsInfo getWorkerTagsInfo;
    private ActivityNgInfoNew activityNgInfos;

    public GetWorkerTagsInfo getGetWorkerTagsInfo() {
        return getWorkerTagsInfo;
    }

    public void setGetWorkerTagsInfo(GetWorkerTagsInfo getWorkerTagsInfo) {
        this.getWorkerTagsInfo = getWorkerTagsInfo;
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

    public ActionEntity getActionEntity() {
        return actionEntity;
    }

    public void setActionEntity(ActionEntity actionEntity) {
        this.actionEntity = actionEntity;
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

    public String getMandarinAbility() {
        return mandarinAbility;
    }

    public void setMandarinAbility(String mandarinAbility) {
        this.mandarinAbility = mandarinAbility;
    }

    public String getPhoneRank() {
        return phoneRank;
    }

    public void setPhoneRank(String phoneRank) {
        this.phoneRank = phoneRank;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public void setWage(String wage) {
        this.wage = wage;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public void setWorkingYears(String workingYears) {
        this.workingYears = workingYears;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void setStature(String stature) {
        this.stature = stature;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setAddress(String address) {
        this.address = address;
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

//    public void setPhotos(List<WorkerPhoto> photos) {
//        this.photos = photos;
//    }

    public void setSystemCertifications(List<WorkerSystemCertification> systemCertifications) {
        this.systemCertifications = systemCertifications;
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

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getJobNumber() {
        return jobNumber;
    }


    public String getWage() {
        return wage;
    }

    public String getDistance() {
        return distance;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public String getWorkingYears() {
        return workingYears;
    }

    public String getEducation() {
        return education;
    }

    public String getHobby() {
        return hobby;
    }

    public String getStature() {
        return stature;
    }

    public String getWeight() {
        return weight;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getConstellation() {
        return constellation;
    }

    public String getSignature() {
        return signature;
    }

    public String getAddress() {
        return address;
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

//    public List<WorkerPhoto> getPhotos() {
//        return photos;
//    }

    public List<WorkerSystemCertification> getSystemCertifications() {
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

//    public static class WorkerPhoto {
//
//        /**
//         * 缩略
//         */
//        private String smallPic;
//
//        /**
//         * 原始
//         */
//        private String hqPic;
//
//        public void setSmallPic(String smallPic) {
//            this.smallPic = smallPic;
//        }
//
//        public void setHqPic(String hqPic) {
//            this.hqPic = hqPic;
//        }
//
//        public String getSmallPic() {
//            return smallPic;
//        }
//
//        public String getHqPic() {
//            return hqPic;
//        }
//    }

    public static class WorkerSystemCertification {

        /**
         * 认证名称
         */
        private String name;

        /**
         * 描述
         */
        private String description;

        /**
         * 图标
         */
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

        WorkerInfo that = (WorkerInfo) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
