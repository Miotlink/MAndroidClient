package com.homepaas.sls.mvp.model;

import android.text.TextUtils;

import com.homepaas.sls.domain.entity.ActionEntity;
import com.homepaas.sls.domain.entity.ActivityNgInfo;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;

import java.util.List;

/**
 * on 2016/1/14 0014
 *
 * @author zhudongjie .
 */
public class WorkerCollectionEntity implements IServiceInfo {

    private boolean showPhoto;

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
     * 是否接单
     */
    private boolean placeOrderEnable;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别 "0":男"1":女
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
     * 血型
     */
    private String bloodType;

    /**
     * 星座
     */
    private String constellation;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 地址
     */
    private String address;

    /**
     * 服务时间 例:每周一至周日 8:00-20:00
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
     * 点赞数
     */
    private int likeCount;

    /**
     * 用户是否点赞
     */
    private boolean like;

    /**
     * 收藏数
     */
    private int collectedCount;

    /**
     * 是否收藏
     */
    private boolean collected;

    /**
     * 接单数
     */
    private String orderCount;

    /**
     * 接单排名
     */
    private CharSequence orderRank;

    /**
     * 被通话次数
     */
    private String phoneCount;

    /**
     * 被通话排名
     */
    private CharSequence phoneRank;

    /**
     * 评分
     */
    private String grade;

    /**
     * 评分排名
     */
    private CharSequence gradeRank;

    /**
     * 认证集合
     */
    private List<SystemCertification> systemCertifications;
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
    private double latitude;
    /**
     * 经度坐标
     */
    private double longitude;

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
     * 是否可通话
     */
    private boolean callable;
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

    //    /**
//     * 头像集合
//     */
//    private List<Photo> photos;



    public boolean noWorkerMessage(){
        if (TextUtils.isEmpty(getSignature()) && TextUtils.isEmpty(getIntro()) && TextUtils.isEmpty(getBloodType())
                && TextUtils.isEmpty(getEducation()) && TextUtils.isEmpty(getNativePlace()) && TextUtils.isEmpty(getWorkingYears())
                && TextUtils.isEmpty(getStature()) && TextUtils.isEmpty(getConstellation()))
            return true;
        return false;
    }
    public WorkerCollectionEntity(String id) {
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

    public ActionEntity getActionEntity() {
        return actionEntity;
    }

    public void setActionEntity(ActionEntity actionEntity) {
        this.actionEntity = actionEntity;
    }

    @Override
    public int type() {
        return TYPE_WORKER;
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
//
//    @Override
//    public List<Photo> getPhotos() {
//        return photos;
//    }

    public String getMandarinAbility() {
        return mandarinAbility;
    }

    public void setMandarinAbility(String mandarinAbility) {
        this.mandarinAbility = mandarinAbility;
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

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
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

    public String getAge() {
        return age;
    }

    public String getGradeNumber() {
        return grade;
    }

    public void setGradeNumber(String gradeNumber) {
        this.grade = gradeNumber;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public CharSequence getGradeRank() {
        return gradeRank;
    }

    public String getGender() {
        return gender;
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

    public String getGrade() {
        return grade;
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

//    public void setPhotos(List<Photo> photos) {
//        this.photos = photos;
//    }

    public void setSystemCertifications(List<SystemCertification> systemCertifications) {
        this.systemCertifications = systemCertifications;
    }

    public CharSequence getPhoneRank() {
        return phoneRank;
    }

    public void setPhoneRank(CharSequence phoneRank) {
        this.phoneRank = phoneRank;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WorkerCollectionEntity{");
        sb.append("showPhoto=").append(showPhoto);
        sb.append(", id='").append(id).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", age='").append(age).append('\'');
        sb.append(", jobNumber='").append(jobNumber).append('\'');
        sb.append(", wage='").append(wage).append('\'');
        sb.append(", distance='").append(distance).append('\'');
        sb.append(", nativePlace='").append(nativePlace).append('\'');
        sb.append(", workingYears='").append(workingYears).append('\'');
        sb.append(", education='").append(education).append('\'');
        sb.append(", hobby='").append(hobby).append('\'');
        sb.append(", stature='").append(stature).append('\'');
        sb.append(", weight='").append(weight).append('\'');
        sb.append(", bloodType='").append(bloodType).append('\'');
        sb.append(", constellation='").append(constellation).append('\'');
        sb.append(", signature='").append(signature).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", serviceTime='").append(serviceTime).append('\'');
        sb.append(", serviceScope='").append(serviceScope).append('\'');
        sb.append(", intro='").append(intro).append('\'');
        sb.append(", likeCount=").append(likeCount);
        sb.append(", like=").append(like);
        sb.append(", collectedCount=").append(collectedCount);
        sb.append(", collected=").append(collected);
        sb.append(", orderCount='").append(orderCount).append('\'');
        sb.append(", orderRank=").append(orderRank);
        sb.append(", phoneCount='").append(phoneCount).append('\'');
        sb.append(", grade='").append(grade).append('\'');
        sb.append(", gradeRank=").append(gradeRank);
        sb.append(", mandarinAbility='").append(mandarinAbility).append('\'');
        sb.append(", workExperience='").append(workExperience).append('\'');
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
//        sb.append(", photos=").append(photos);
        sb.append(", systemCertifications=").append(systemCertifications);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkerCollectionEntity infoModel = (WorkerCollectionEntity) o;

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
