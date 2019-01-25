package com.homepaas.sls.data.entity;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.data.network.dataformat.BusinessTagsBody;
import com.homepaas.sls.data.network.dataformat.WorkerTagsBody;
import com.homepaas.sls.data.validator.ttl.TtlObject;
import com.homepaas.sls.domain.entity.ActionEntity;
import com.homepaas.sls.domain.entity.ActivityNgInfo;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.ServiceType;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * on 2016/1/13 0013
 *
 * @author zhudongjie .
 */
@DatabaseTable(tableName = "tb_business")
public class BusinessEntity implements TtlObject {

    public static final int FROM_COLLECTION = 1;

    public static final int FROM_SEARCH = 1 << 1;

    /**
     * 商户ID
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
     * 是否可以拨打电话	1： 可
     *0： 不可
     */
    @SerializedName("IsCall")
    @DatabaseField
    private String isCall;

    /**
     *是否接单	1：接单
     *其它：暂停接单
     */
    @SerializedName("AcceptOrder")
    private String allowAcceptOrder;

    /**
     * 店名
     */
    @SerializedName("Name")
    @DatabaseField
    private String name;

    /**
     * 距离
     */
    @SerializedName("Distance")
    @DatabaseField
    private String distance;

    /**
     * 性质
     */
    @SerializedName("Property")
    @DatabaseField
    private String property;

    /**
     * 成立时间
     */
    @SerializedName("EstablishedTime")
    private String establishedTime;

    /**
     * 主营业务
     */
    @SerializedName("MainBusiness")
    @DatabaseField
    private String mainBusiness;

    /**
     * 营业面积
     */
    @SerializedName("Area")
    @DatabaseField
    private String area;

    /**
     * 经营规模
     */
    @SerializedName("Scale")
    @DatabaseField
    private String scale;

    /**
     * 员工人数
     */
    @SerializedName("StaffNumber")
    @DatabaseField
    private String staffNumber;

    /**
     * 税务登记号
     */
    @SerializedName("TexId")
    @DatabaseField
    private String texId;

    /**
     * 营业执照注册号
     */
    @SerializedName("LicenseId")
    @DatabaseField
    private String licenseId;

    /**
     * 地址
     */
    @SerializedName("Address")
    @DatabaseField
    private String address;

    /**
     * 时间
     */
    @SerializedName("ServiceTime")
    @DatabaseField
    private String serviceTime;

    /**
     *服务范围
     */
    @SerializedName("ServiceScope")
    @DatabaseField
    private String serviceScope;

    /**
     * 商户介绍
     */
    @SerializedName("Intro")
    @DatabaseField
    private String intro;

    /**
     *点赞数
     */
    @SerializedName("PraiseCount")
    @DatabaseField
    private String praiseCount;

    /**
     * 用户是否点赞	0:否;1:是
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
     * 是否收藏	0:否;1:是
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
     * 接单排名
     */
    @SerializedName("OrderRank")
    @DatabaseField
    private String orderRank;

    /**
     *被通话次数
     */
    @SerializedName("PhoneCount")
    @DatabaseField
    private String phoneCount;

    /**
     * 被通话排名
     */
    @SerializedName("PhoneRank")
    @DatabaseField
    private String phoneRank;

    /**
     *评分
     */
    @SerializedName("Grade")
    @DatabaseField
    private String grade;

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
    private Collection<SystemCertification> systemCertifications;

    /**
     *纬度坐标
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
     * 个性签名
     */
    @SerializedName("Signature")
    @DatabaseField
    private String signature;

    /**
     * 商户号
     */
    @SerializedName("Number")
    @DatabaseField
    private String number;

    /**
     * 默认服务
     */
    @SerializedName("DefaultService")
//    @DatabaseField
    private ServiceType defaultService;

    @SerializedName("Services")
//    @DatabaseField
    private List<ServiceType> services;

    @SerializedName("Photos")
    @ForeignCollectionField(eager = true)
    private Collection<Photo> photos;

    @SerializedName("LicencePhotos")
    private List<Photo> licencePhotos;

    @SerializedName("TagList")
    private BusinessTagsBody businessTagsBody;
    @SerializedName("ActivityNgs")
    private ActivityNgInfoNew activityNgInfos;

    /**
     * 是否有到店支付
     */
    @SerializedName("IsStorePay")
    private String isStorePay;

    /**
     * 到店支付文案
     */
    @SerializedName("StorePayStr")
    private String storePayStr;

    @SerializedName("Activity")
    private ActionEntity actionEntity;

    /**
     * 接单数
     */
    @SerializedName("OrderCount2")
    private String orderCount2;
    /**
     *被通话次数
     */
    @SerializedName("PhoneCount2")
    private String phoneCount2;

    /**
     *评分
     */
    @SerializedName("Grade2")
    private String grade2;

    public ActionEntity getActionEntity() {
        return actionEntity;
    }

    public void setActionEntity(ActionEntity actionEntity) {
        this.actionEntity = actionEntity;
    }

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

    public String getGrade2() {
        return grade2;
    }

    public void setGrade2(String grade2) {
        this.grade2 = grade2;
    }

    public String getPhoneCount2() {
        return phoneCount2;
    }

    public void setPhoneCount2(String phoneCount2) {
        this.phoneCount2 = phoneCount2;
    }

    public List<Photo> getLicencePhotos() {
        return licencePhotos;
    }

    public void setLicencePhotos(List<Photo> licencePhotos) {
        this.licencePhotos = licencePhotos;
    }

    public BusinessTagsBody getBusinessTagsBody() {
        return businessTagsBody;
    }

    public void setBusinessTagsBody(BusinessTagsBody businessTagsBody) {
        this.businessTagsBody = businessTagsBody;
    }

    public ActivityNgInfoNew getActivityNgInfos() {
        return activityNgInfos;
    }

    public void setActivityNgInfos(ActivityNgInfoNew activityNgInfos) {
        this.activityNgInfos = activityNgInfos;
    }

    @DatabaseField
    private long persistedTime;

    public Collection<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Collection<Photo> photos) {
        this.photos = photos;
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

    public void setSystemCertifications(Collection<SystemCertification> systemCertifications) {
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


    public Collection<SystemCertification> getSystemCertifications() {
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

    public void setAllowAcceptOrder(String allowAcceptOrder) {
        this.allowAcceptOrder = allowAcceptOrder;
    }

    public String getAllowAcceptOrder() {
        return allowAcceptOrder;
    }

    @DatabaseTable(tableName = "tb_business_photo")
    public static class Photo {

        @DatabaseField(foreign = true, foreignColumnName = "id")
        private BusinessEntity business;

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

        public BusinessEntity getBusiness() {
            return business;
        }

        public void setBusiness(BusinessEntity business) {
            this.business = business;
        }
    }

    @DatabaseTable(tableName = "tb_business_systemCertification")
    public static class SystemCertification {

        @DatabaseField(foreign = true, foreignColumnName = "id")
        private BusinessEntity business;

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

        public BusinessEntity getBusiness() {
            return business;
        }

        public void setBusiness(BusinessEntity business) {
            this.business = business;
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
