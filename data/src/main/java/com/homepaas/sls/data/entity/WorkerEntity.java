package com.homepaas.sls.data.entity;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.data.network.dataformat.WorkerTagsBody;
import com.homepaas.sls.data.validator.ttl.TtlObject;
import com.homepaas.sls.domain.entity.ActionEntity;
import com.homepaas.sls.domain.entity.ActivityNgInfo;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.ServiceType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;
import java.util.List;

/**
 * on 2016/1/12 0012
 *
 * @author zhudongjie .
 */
@DatabaseTable(tableName = "tb_worker")
public class WorkerEntity implements TtlObject {

    public static final int FROM_COLLECTION = 1;

    public static final int FROM_SEARCH = 1 << 1;

    /**
     * 工人ID
     */
    @SerializedName("Id")
    @DatabaseField(id = true)
    private String id;

    /**
     * 0x01 collection,0x02 search
     */
    @DatabaseField
    private int dataFrom;

    /**
     * 默认头像
     */
    @SerializedName("Photo")
    @DatabaseField
    private String photo;

    /**
     * 头像数量
     */
    @SerializedName("PhotoCount")
    @DatabaseField
    private String photoCount;

    /**
     * 电话号码
     */
    @SerializedName("PhoneNumber")
    @DatabaseField
    private String phoneNumber;
    /**
     * 是否接单	1：接单
     *其它：暂停接单
     */
    @SerializedName("AcceptOrder")
    @DatabaseField
    private String allowAcceptOrder;

    /**
     * 姓名
     */
    @SerializedName("Name")
    @DatabaseField
    private String name;

    /**
     * 性别 “0”:男;”1”:女.
     */
    @SerializedName("Gender")
    @DatabaseField
    private String gender;

    /**
     * 年龄
     */
    @SerializedName("Age")
    @DatabaseField
    private String age;

    /**
     * 工号
     */
    @SerializedName("JobNumber")
    @DatabaseField
    private String jobNumber;

    /**
     * 时薪
     */
    @SerializedName("Wage")
    @DatabaseField
    private String wage;

    /**
     * 距离
     */
    @SerializedName("Distance")
    @DatabaseField
    private String distance;

    /**
     * 籍贯
     */
    @SerializedName("NativePlace")
    @DatabaseField
    private String nativePlace;

    /**
     * 工作年限
     */
    @SerializedName("WorkingYears")
    @DatabaseField
    private String workingYears;

    /**
     * 学历
     */
    @SerializedName("Education")
    @DatabaseField
    private String education;

    /**
     * 爱好
     */
    @SerializedName("Hobby")
    @DatabaseField
    private String hobby;

    /**
     * 身高
     */
    @SerializedName("Stature")
    @DatabaseField
    private String stature;

    /**
     * 体重
     */
    @SerializedName("Weight")
    @DatabaseField
    private String weight;

    /**
     * 血型
     */
    @SerializedName("BloodType")
    @DatabaseField
    private String bloodType;

    /**
     * 星座
     */
    @SerializedName("Constellation")
    @DatabaseField
    private String constellation;

    /**
     * 个性签名
     */
    @SerializedName("Signature")
    @DatabaseField
    private String signature;

    /**
     * 地址
     */
    @SerializedName("Address")
    @DatabaseField
    private String address;


    /**
     * 服务时间 例:每周一至周日 8:00-20:00
     */
    @SerializedName("ServiceTime")
    @DatabaseField
    private String serviceTime;

    /**
     * 服务范围
     */
    @SerializedName("ServiceScope")
    @DatabaseField
    private String serviceScope;

    /**
     * 系统介绍
     */
    @SerializedName("Intro")
    @DatabaseField
    private String intro;

    /**
     * 点赞数
     */
    @SerializedName("PraiseCount")
    @DatabaseField
    private String praiseCount;

    /**
     * 用户是否点赞:0:否;1:是
     */
    @SerializedName("IsPraise")
    @DatabaseField
    private String isPraise;


    /**
     * 收藏数
     */
    @SerializedName("FavoriteCount")
    @DatabaseField
    private String favoriteCount;

    /**
     * 是否收藏:0:否;1:是
     */
    @SerializedName("IsFavorite")
    @DatabaseField
    private String isFavorite;

    /**
     * 接单数
     */
    @SerializedName("OrderCount")
    @DatabaseField
    private String orderCount;
    /**
     * 接单数
     */
    @SerializedName("OrderCount2")
    private String orderCount2;



    /**
     * 接单排名
     */
    @SerializedName("OrderRank")
    @DatabaseField
    private String orderRank;

    /**
     * 被通话排名
     */
    @SerializedName("PhoneRank")
    @DatabaseField
    private String phoneRank;


    /**
     * 被通话次数
     */
    @SerializedName("PhoneCount")
    @DatabaseField
    private String phoneCount;

    /**
     * 被通话次数
     */
    @SerializedName("PhoneCount2")

    private String phoneCount2;
    /**
     * 评分
     */
    @SerializedName("Grade")
    @DatabaseField
    private String grade;
    /**
     * 评分
     */
    @SerializedName("Grade2")

    private String grade2;

    /**
     * 评分排名
     */
    @SerializedName("GradeRank")
    @DatabaseField
    private String gradeRank;

    /**
     * 认证集合
     */
    @SerializedName("SystemCertification")
    @ForeignCollectionField(eager = true)
    private Collection<SystemCertification> systemCertification;
    /**
     * 普通话水平
     */
    @SerializedName("MandarinAbility")
    @DatabaseField
    private String mandarinAbility;

    /**
     *工作经历
     */
    @SerializedName("WorkExperience")
    @DatabaseField
    private String workExperience;


    /**
     * 纬度坐标
     */
    @SerializedName("Latitude")
    @DatabaseField
    private String latitude;

    /**
     * 经度坐标
     */
    @SerializedName("Longitude")
    @DatabaseField
    private String longitude;

    /**
     * 默认服务
     */
    @SerializedName("DefaultService")
    private ServiceType defaultService;

    /**
     * 所有服务	ServiceType集合
     */
    @SerializedName("Services")
    private List<ServiceType> services;

    /**
     *活动信息	当前工人参与的活动信息，见活动信息定义
     */
    @SerializedName("Activity")
    private ActionEntity actionEntity;


    /**
     * 是否可通话
     */
    @SerializedName("IsCall")
    @DatabaseField
    private String isCall;

    @SerializedName("TagList")
    private WorkerTagsBody workerTagsBody;
    @SerializedName("ActivityNgs")
    private ActivityNgInfoNew activityNgInfos;

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

    public WorkerTagsBody getWorkerTagsBody() {
        return workerTagsBody;
    }

    public void setWorkerTagsBody(WorkerTagsBody workerTagsBody) {
        this.workerTagsBody = workerTagsBody;
    }

    public ActivityNgInfoNew getActivityNgInfos() {
        return activityNgInfos;
    }

    public void setActivityNgInfos(ActivityNgInfoNew activityNgInfos) {
        this.activityNgInfos = activityNgInfos;
    }

    @DatabaseField
    private long persistedTime;

    public static int getFromCollection() {
        return FROM_COLLECTION;
    }

    public static int getFromSearch() {
        return FROM_SEARCH;
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

    public ServiceType getDefaultService() {
        return defaultService;
    }

    public void setDefaultService(ServiceType defaultService) {
        this.defaultService = defaultService;
    }

    public List<ServiceType> getServices() {
        return services;
    }

    public void setServices(List<ServiceType> services) {
        this.services = services;
    }

    public ActionEntity getActionEntity() {
        return actionEntity;
    }

    public void setActionEntity(ActionEntity actionEntity) {
        this.actionEntity = actionEntity;
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

    public void setSystemCertification(Collection<SystemCertification> systemCertification) {
        this.systemCertification = systemCertification;
    }

    public String getPhoneRank() {
        return phoneRank;
    }

    public void setPhoneRank(String phoneRank) {
        this.phoneRank = phoneRank;
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

    public Collection<SystemCertification> getSystemCertification() {
        return systemCertification;
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

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public int getDataFrom() {
        return dataFrom;
    }

    public void setDataFrom(int dataFrom) {
        this.dataFrom = dataFrom;
    }

    @Override
    public long getPersistedTime() {
        return persistedTime;
    }

    public void setPersistedTime(long persistedTime) {
        this.persistedTime = persistedTime;
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

    @DatabaseTable(tableName = "tb_worker_photo")
    public static class Photo {

        @DatabaseField(foreign = true, foreignColumnName = "id")
        private WorkerEntity worker;

        /**
         * 缩略图
         */
        @SerializedName("SmallPic")
        @DatabaseField
        private String smallPic;

        /**
         * 原始图
         */
        @SerializedName("HqPic")
        @DatabaseField
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

        public WorkerEntity getWorker() {
            return worker;
        }

        public void setWorker(WorkerEntity worker) {
            this.worker = worker;
        }
    }

    @DatabaseTable(tableName = "tb_worker_systemCertification")
    public static class SystemCertification {

        @DatabaseField(foreign = true, foreignColumnName = "id")
        private WorkerEntity worker;

        /**
         * 认证名称
         */
        @SerializedName("Name")
        @DatabaseField
        private String name;

        /**
         * 描述
         */
        @SerializedName("Description")
        @DatabaseField
        private String description;

        /**
         * 图标
         */
        @SerializedName("Image")
        @DatabaseField
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

        public WorkerEntity getWorker() {
            return worker;
        }

        public void setWorker(WorkerEntity worker) {
            this.worker = worker;
        }
    }

    public static class Service {

        @SerializedName("DefaultServiceType")
        private DefaultService defaultService;

        @SerializedName("ServiceTypeList")
        private List<String> serviceTypeList;

        public static class DefaultService {

            @SerializedName("Id")
            private String id;

            @SerializedName("Name")
            private String name;

            public DefaultService() {
                //need
            }

            public DefaultService(String id, String name) {
                this.id = id;
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public DefaultService getDefaultService() {
            return defaultService;
        }

        public void setDefaultService(DefaultService defaultService) {
            this.defaultService = defaultService;
        }

        public List<String> getServiceTypeList() {
            return serviceTypeList;
        }

        public void setServiceTypeList(List<String> serviceTypeList) {
            this.serviceTypeList = serviceTypeList;
        }
    }
}
