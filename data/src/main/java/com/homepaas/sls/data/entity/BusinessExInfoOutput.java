package com.homepaas.sls.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mhy on 2017/12/28.
 */

public class BusinessExInfoOutput implements Serializable {
    private static final long serialVersionUID = 2368097249284178426L;

    /**
     * Id (string, optional): 商户或者普通工人的Id ,
     * UserId (string, optional): 商户或者普通工人的UserId ,
     * UserType (string, optional): 类型 2：工人，3：商户 ,
     * Name (string, optional): 名字 ,
     * AvatarUrl (string, optional): 头像。 商户头像为门面照。普通工人头像为头像。 ,
     * PhoneNumber (string, optional): 电话 ,
     * Grade (string, optional): 评分。 按最近20单平均算，精确到小数点后一位，小数点第二位四舍五入,默认初始值为5分 ,
     * ServiceTime (string, optional): 服务时间。 如果后台没有数据，默认为9：00-18：00。 ,
     * WorkeOfYears (string, optional): 工作年限。 商户显示的是“成立时间”。普通工人显示的是“从业年限”。 ,
     * BusinessProperty (string, optional): 商家性质。 商户显示的是“商户性质”。普通工人显示的是”个人“（后台工人没有个人这个字段）。 ,
     * Address (string, optional): 商家地址。 商户显示的是“商户地址”。普通工人显示的是”现居地址“。 ,
     * ServiceAera (string, optional): 服务范围。 商户显示的是“提供服务距离”。普通工人显示的是”可上门距离“。 ,
     * PhotoUrls (Array[YJ.Caller.ClientApi.Models.BusinessEx.PhotoUrls], optional): 照片。 商户显示的是“门面照+内部照+服务照”。普通工人显示的是”头像“。点击白色的整块区域，打开新的页面： ,
     * BusinessCertifications (Array[YJ.Caller.ClientApi.Models.BusinessEx.PhotoUrls], optional): 商家资质。 只有商户有该模块，若部分商家没上传也不显示该模块。显示的是营业执照的方形缩略图。 点击缩略图后显示原图，点击原图关闭原图。
     */

    private String Id;
    private String UserType;
    private String Name;
    private String AvatarUrl;
    private String PhoneNumber;
    private float Grade;
    private String ServiceTime;
    private String WorkeOfYears;
    private String BusinessProperty;
    private String Address;
    private String ServiceAera;
    private String UserId;//(string, optional): 商户或者普通工人的UserId ,
    private List<PhotoUrlsBean> PhotoUrls;
    private List<BusinessCertificationsBean> BusinessCertifications;

    public String getId() {
        return Id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String UserType) {
        this.UserType = UserType;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAvatarUrl() {
        return AvatarUrl;
    }

    public void setAvatarUrl(String AvatarUrl) {
        this.AvatarUrl = AvatarUrl;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public float getGrade() {
        return Grade;
    }

    public void setGrade(float Grade) {
        this.Grade = Grade;
    }

    public String getServiceTime() {
        return ServiceTime;
    }

    public void setServiceTime(String ServiceTime) {
        this.ServiceTime = ServiceTime;
    }

    public String getWorkeOfYears() {
        return WorkeOfYears;
    }

    public void setWorkeOfYears(String WorkeOfYears) {
        this.WorkeOfYears = WorkeOfYears;
    }

    public String getBusinessProperty() {
        return BusinessProperty;
    }

    public void setBusinessProperty(String BusinessProperty) {
        this.BusinessProperty = BusinessProperty;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getServiceAera() {
        return ServiceAera;
    }

    public void setServiceAera(String ServiceAera) {
        this.ServiceAera = ServiceAera;
    }

    public List<PhotoUrlsBean> getPhotoUrls() {
        return PhotoUrls;
    }

    public void setPhotoUrls(List<PhotoUrlsBean> PhotoUrls) {
        this.PhotoUrls = PhotoUrls;
    }

    public List<BusinessCertificationsBean> getBusinessCertifications() {
        return BusinessCertifications;
    }

    public void setBusinessCertifications(List<BusinessCertificationsBean> BusinessCertifications) {
        this.BusinessCertifications = BusinessCertifications;
    }

    public static class PhotoUrlsBean implements Serializable {
        private static final long serialVersionUID = -8781754692286660093L;
        /**
         * SmallPic (string, optional): 缩略图 ,
         * HqPic (string, optional): 高清图
         */

        private String SmallPic;
        private String HqPic;

        public String getSmallPic() {
            return SmallPic;
        }

        public void setSmallPic(String SmallPic) {
            this.SmallPic = SmallPic;
        }

        public String getHqPic() {
            return HqPic;
        }

        public void setHqPic(String HqPic) {
            this.HqPic = HqPic;
        }
    }

    public static class BusinessCertificationsBean implements Serializable {
        private static final long serialVersionUID = -2529783933197006847L;
        /**
         * SmallPic : string
         * HqPic : string
         */

        private String SmallPic;
        private String HqPic;

        public String getSmallPic() {
            return SmallPic;
        }

        public void setSmallPic(String SmallPic) {
            this.SmallPic = SmallPic;
        }

        public String getHqPic() {
            return HqPic;
        }

        public void setHqPic(String HqPic) {
            this.HqPic = HqPic;
        }
    }
}
