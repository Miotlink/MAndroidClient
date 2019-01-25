package com.homepaas.sls.domain.param;

/**
 * Created by JWC on 2017/3/29.
 */

public class PlaceOrderParam {

    private String WorkerOrMerchantUserId;//选择商户或工人的标识 "" 标识系统默认推荐，不为空则为用户指定预约的商户或工人

    private String token;
    //服务Id（对应四级的Id）
    private String serviceId;
    //（对应3级的字段）是否是特殊类型（保姆，月嫂，看护类等等），0 不是，1是;
    private String specialType;
    //订单来源（0:AndroidApp,1:IOSApp）
    private String orderFrom;
    /**
     * 当SpecialType=1时，下面几个字段为必填
     */
    //联系人
    private String contact;
    //Gender：0：男士，1：女士
    private String gender;
    //电话
    private String phoneNumber;
    /**
     * 当SpecialType=0时，下面几个字段为必填
     */
    //服务地址Id
    private String serviceAddressId;
    //购买的数量
    private String total;
    //预约的时间
    private String serviceStartAt;
    //购买的单价
    private String price;
    //（对应3级的字段）出售类型字段1:定价类型(包含定价面议混合类型); 0:面议类型;
    private String sellType;
    //备注信息
    private String serviceContent;
    //是否购买了保险
    private String isClaims;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getWorkerOrMerchantUserId() {
        return WorkerOrMerchantUserId;
    }

    public void setWorkerOrMerchantUserId(String workerOrMerchantUserId) {
        WorkerOrMerchantUserId = workerOrMerchantUserId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getSpecialType() {
        return specialType;
    }

    public void setSpecialType(String specialType) {
        this.specialType = specialType;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getServiceAddressId() {
        return serviceAddressId;
    }

    public void setServiceAddressId(String serviceAddressId) {
        this.serviceAddressId = serviceAddressId;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getServiceStartAt() {
        return serviceStartAt;
    }

    public void setServiceStartAt(String serviceStartAt) {
        this.serviceStartAt = serviceStartAt;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSellType() {
        return sellType;
    }

    public void setSellType(String sellType) {
        this.sellType = sellType;
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getIsClaims() {
        return isClaims;
    }

    public void setIsClaims(String isClaims) {
        this.isClaims = isClaims;
    }
}
