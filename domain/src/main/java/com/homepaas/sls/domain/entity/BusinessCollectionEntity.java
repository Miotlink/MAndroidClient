package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */

public class BusinessCollectionEntity implements Serializable {

    /**
     * 商户ID
     */
    @SerializedName("Id")
    private String id;

    @SerializedName("DisplayAttribute")
    private String displayAttribute;
    /**
     * 电话号码
     */
    @SerializedName("PhoneNumber")
    private String phoneNumber;

    /**
     * 是否可以拨打电话	1： 可
     *0： 不可
     */
    @SerializedName("IsCall")
    private String isCall;

    /**
     *是否接单	1：接单
     *其它：暂停接单
     */
    @SerializedName("AllowAcceptOrder")
    private String allowAcceptOrder;

    /**
     * 店名
     */
    @SerializedName("Name")
    private String name;

    /**
     * 距离
     */
    @SerializedName("Distance")
    private String distance;

    /**
     * 性质
     */
    @SerializedName("Property")
    private String property;

    /**
     * 成立时间
     */
    @SerializedName("EstablishedTime")
    private String establishedTime;

    @SerializedName("EstablishedTimeShort")
    private String establishedTimeShort;
    /**
     * 主营业务
     */
    @SerializedName("MainBusiness")
    private String mainBusiness;

    /**
     * 营业面积
     */
    @SerializedName("Area")
    private String area;

    /**
     * 经营规模
     */
    @SerializedName("Scale")
    private String scale;

    /**
     * 员工人数
     */
    @SerializedName("StaffNumber")
    private String staffNumber;

    /**
     * 税务登记号
     */
    @SerializedName("TexId")
    private String texId;

    /**
     * 营业执照注册号
     */
    @SerializedName("LicenseId")
    private String licenseId;

    /**
     * 地址
     */
    @SerializedName("Address")
    private String address;

    /**
     * 时间
     */
    @SerializedName("ServiceTime")
    private String serviceTime;

    /**
     *服务范围
     */
    @SerializedName("ServiceScope")
    private String serviceScope;

    /**
     * 商户介绍
     */
    @SerializedName("Intro")
    private String intro;

    @SerializedName("IsWholeMerchant")
    private String isWholeMerchant;
    /**
     *点赞数
     */
    @SerializedName("PraiseCount")
    private String praiseCount;

    /**
     * 用户是否点赞	0:否;1:是
     */
    @SerializedName("IsPraise")
    private String isPraise;

    /**
     * 收藏数
     */
    @SerializedName("FavoriteCount")
    private String favoriteCount;

    /**
     * 是否收藏	0:否;1:是
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
     *被通话次数
     */
    @SerializedName("PhoneCount")
    private String phoneCount;

    /**
     * 被通话排名
     */
    @SerializedName("PhoneRank")
    private String phoneRank;

    /**
     *评分
     */
    @SerializedName("Grade")
    private String grade;
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
    private List<SystemCertification> systemCertifications;

    /**
     *纬度坐标
     */
    @SerializedName("Latitude")
    private String latitude;

    /**
     * 经度坐标
     */
    @SerializedName("Longitude")
    private String longitude;

    /**
     * 个性签名
     */
    @SerializedName("Signature")
    private String signature;

    /**
     * 商户号
     */
    @SerializedName("Number")
    private String number;

    /**
     * 所有服务	ServiceType集合
     */
    @SerializedName("Services")
    private Service services;

    @SerializedName("Photos")
    private List<Photo> photos;

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

    public String getEstablishedTimeShort() {
        return establishedTimeShort;
    }

    public void setEstablishedTimeShort(String establishedTimeShort) {
        this.establishedTimeShort = establishedTimeShort;
    }

    public String getIsWholeMerchant() {
        return isWholeMerchant;
    }

    public void setIsWholeMerchant(String isWholeMerchant) {
        this.isWholeMerchant = isWholeMerchant;
    }

    public Service getServices() {
        return services;
    }

    public void setServices(Service services) {
        this.services = services;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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


    public List<SystemCertification> getSystemCertifications() {
        return systemCertifications;
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

    public String getIsCall() {
        return isCall;
    }

    public void setIsCall(String isCall) {
        this.isCall = isCall;
    }

    public void setAllowAcceptOrder(String allowAcceptOrder) {
        this.allowAcceptOrder = allowAcceptOrder;
    }

    public String getAllowAcceptOrder() {
        return allowAcceptOrder;
    }

    public static class Photo implements Serializable {


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

    public static class SystemCertification implements Serializable{


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

    public static class Service implements Serializable{

        @SerializedName("DefaultServiceType")
        private DefaultService defaultService;

        @SerializedName("ServiceTypeList")
        private List<String> serviceTypeList;

        @SerializedName("Services")
        private List<DefaultService> serviceList;

        public static class DefaultService implements Serializable{

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
