package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/28.
 */

public class WorkerCollectionEntity  implements Serializable {

    /**
     * 工人ID
     */
    @SerializedName("Id")
    private String id;

    /**
     * 默认头像
     */
    @SerializedName("Photos")
    private List<Photo> photo;


    /**
     * 电话号码
     */
    @SerializedName("PhoneNumber")
    private String phoneNumber;
    /**
     * 是否接单	1：接单
     *其它：暂停接单
     */
    @SerializedName("AllowAcceptOrder")
    private String allowAcceptOrder;

    /**
     * 姓名
     */
    @SerializedName("Name")
    private String name;

    /**
     * 性别 “0”:男;”1”:女.
     */
    @SerializedName("Gender")
    private String gender;

    /**
     * 年龄
     */
    @SerializedName("Age")
    private String age;

    /**
     * 工号
     */
    @SerializedName("JobNumber")
    private String jobNumber;

    /**
     * 时薪
     */
    @SerializedName("Wage")
    private String wage;

    /**
     * 距离
     */
    @SerializedName("Distance")
    private String distance;

    /**
     * 籍贯
     */
    @SerializedName("NativePlace")
    private String nativePlace;

    /**
     * 工作年限
     */
    @SerializedName("WorkingYears")
    private String workingYears;

    /**
     * 学历
     */
    @SerializedName("Education")
    private String education;

    /**
     * 爱好
     */
    @SerializedName("Hobby")
    private String hobby;

    /**
     * 身高
     */
    @SerializedName("Stature")
    private String stature;

    /**
     * 体重
     */
    @SerializedName("Weight")
    private String weight;

    /**
     * 血型
     */
    @SerializedName("BloodType")
    private String bloodType;

    /**
     * 星座
     */
    @SerializedName("Constellation")
    private String constellation;

    /**
     * 个性签名
     */
    @SerializedName("Signature")
    private String signature;

    /**
     * 地址
     */
    @SerializedName("Address")
    private String address;


    /**
     * 服务时间 例:每周一至周日 8:00-20:00
     */
    @SerializedName("ServiceTime")
    private String serviceTime;

    /**
     * 服务范围
     */
    @SerializedName("ServiceScope")
    private String serviceScope;

    /**
     * 系统介绍
     */
    @SerializedName("Intro")
    private String intro;

    /**
     * 点赞数
     */
    @SerializedName("PraiseCount")
    private String praiseCount;

    /**
     * 用户是否点赞:0:否;1:是
     */
    @SerializedName("IsPraise")
    private String isPraise;


    /**
     * 收藏数
     */
    @SerializedName("FavoriteCount")
    private String favoriteCount;

    /**
     * 是否收藏:0:否;1:是
     */
    @SerializedName("IsFavorite")
    private String isFavorite;

    /**
     * 接单数
     */
    @SerializedName("OrderCount")
    private String orderCount;


    /**
     * 接单排名
     */
    @SerializedName("OrderRank")
    private String orderRank;

    /**
     * 被通话排名
     */
    @SerializedName("PhoneRank")
    private String phoneRank;


    /**
     * 被通话次数
     */
    @SerializedName("PhoneCount")
    private String phoneCount;

    /**
     * 评分
     */
    @SerializedName("Grade")
    private String grade;

    /**
     * 评分
     */
    @SerializedName("Grade2")
    private String gradeNumber;

    /**
     * 评分排名
     */
    @SerializedName("GradeRank")
    private String gradeRank;

    /**
     * 认证集合
     */
    @SerializedName("SystemCertification")
    private List<SystemCertification> systemCertification;
    /**
     * 普通话水平
     */
    @SerializedName("MandarinAbility")
    private String mandarinAbility;

    /**
     *工作经历
     */
    @SerializedName("WorkExperience")
    private String workExperience;

    @SerializedName("DisplayAttribute")
    private String displayAttribute;
    @SerializedName("IsWholeWorker")
    private String sWholeWorker;
    /**
     * 纬度坐标
     */
    @SerializedName("Latitude")
    private String latitude;

    /**
     * 经度坐标
     */
    @SerializedName("Longitude")
    private String longitude;


    /**
     * 所有服务	ServiceType集合
     */
    @SerializedName("Services")
    private Service services;


    /**
     *活动信息	当前工人参与的活动信息，见活动信息定义
     */
    @SerializedName("Activity")
    private ActionEntity actionEntity;

    /**
     * 是否可通话
     */
    @SerializedName("IsCall")
    private String isCall;

    public String getGradeNumber() {
        return gradeNumber;
    }

    public void setGradeNumber(String gradeNumber) {
        this.gradeNumber = gradeNumber;
    }

    public String getDisplayAttribute() {
        return displayAttribute;
    }

    public void setDisplayAttribute(String displayAttribute) {
        this.displayAttribute = displayAttribute;
    }

    public String getsWholeWorker() {
        return sWholeWorker;
    }

    public void setsWholeWorker(String sWholeWorker) {
        this.sWholeWorker = sWholeWorker;
    }

    public Service getServices() {
        return services;
    }

    public void setServices(Service services) {
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

    public List<Photo> getPhoto() {
        return photo;
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

    public void setSystemCertification(List<SystemCertification> systemCertification) {
        this.systemCertification = systemCertification;
    }

    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
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

    public List<SystemCertification> getSystemCertification() {
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

    public static class Photo implements Serializable{
        /**
         * 缩略图
         */
        @SerializedName("SmallPic")
        private String smallPic;
        /**
         * 原始图
         */
        @SerializedName("HqPic")
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
    
    public static class SystemCertification  implements Serializable{

        /**
         * 认证名称
         */
        @SerializedName("Name")
        private String name;

        /**
         * 描述
         */
        @SerializedName("Description")
        private String description;

        /**
         * 图标
         */
        @SerializedName("Image")
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

    public static class Service  implements Serializable{

        @SerializedName("DefaultServiceType")
        private DefaultService defaultService;

        @SerializedName("ServiceTypeList")
        private List<String> serviceTypeList;

        @SerializedName("Services")
        private List<DefaultService> serviceList;

        public static class DefaultService  implements Serializable{

            @SerializedName("DisplayAttribute")
            private String displayAttribute;
            @SerializedName("Id")
            private String id;

            @SerializedName("Name")
            private String name;
            @SerializedName("Icon1")
            private String icon1;
            @SerializedName("Icon2")
            private String icon2;

            public DefaultService() {
                //need
            }

            public String getDisplayAttribute() {
                return displayAttribute;
            }

            public void setDisplayAttribute(String displayAttribute) {
                this.displayAttribute = displayAttribute;
            }

            public String getIcon1() {
                return icon1;
            }

            public void setIcon1(String icon1) {
                this.icon1 = icon1;
            }

            public String getIcon2() {
                return icon2;
            }

            public void setIcon2(String icon2) {
                this.icon2 = icon2;
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

        public List<DefaultService> getServiceList() {
            return serviceList;
        }

        public void setServiceList(List<DefaultService> serviceList) {
            this.serviceList = serviceList;
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
