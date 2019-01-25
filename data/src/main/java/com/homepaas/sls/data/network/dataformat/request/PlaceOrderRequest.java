package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.param.PlaceOrderParam;

/**
 * Created by JWC on 2017/3/29.
 */

public class PlaceOrderRequest {
    @SerializedName("Token")
    private String token;
    //服务Id（对应四级的Id）
    @SerializedName("ServiceId")
    private String serviceId;
    //（对应3级的字段）是否是特殊类型（保姆，月嫂，看护类等等），0 不是，1是;
    @SerializedName("SpecialType")
    private String specialType;
    //订单来源（0:AndroidApp,1:IOSApp）
    @SerializedName("OrderFrom")
    private String orderFrom;
    /**
     * 当SpecialType=1时，下面几个字段为必填
     */
    //联系人
    @SerializedName("Contact")
    private String contact;
    //Gender：0：男士，1：女士
    @SerializedName("Gender")
    private String gender;
    //电话
    @SerializedName("PhoneNumber")
    private String phoneNumber;
    /**
     * 当SpecialType=0时，下面几个字段为必填
     */
    //服务地址Id
    @SerializedName("ServiceAddressId")
    private String serviceAddressId;
    //购买的数量
    @SerializedName("Total")
    private String total;
    //预约的时间
    @SerializedName("ServiceStartAt")
    private String serviceStartAt;
    //购买的单价
    @SerializedName("Price")
    private String price;
    //（对应3级的字段）出售类型字段1:定价类型(包含定价面议混合类型); 0:面议类型;
    @SerializedName("SellType")
    private String sellType;
    //备注留言
    @SerializedName("ServiceContent")
    private String serviceContent;
    //是否购买了保险
    @SerializedName("IsClaims")
    private String isClaims;


    @SerializedName("WorkerOrMerchantUserId")
    private String workerOrMerchantUserId;

    public PlaceOrderRequest(PlaceOrderParam placeOrderParam) {
        this.token = placeOrderParam.getToken();
        this.serviceId = placeOrderParam.getServiceId();
        this.specialType = placeOrderParam.getSpecialType();
        this.orderFrom = placeOrderParam.getOrderFrom();
        this.contact = placeOrderParam.getContact();
        this.gender = placeOrderParam.getGender();
        this.phoneNumber = placeOrderParam.getPhoneNumber();
        this.serviceAddressId = placeOrderParam.getServiceAddressId();
        this.total = placeOrderParam.getTotal();
        this.serviceStartAt = placeOrderParam.getServiceStartAt();
        this.price = placeOrderParam.getPrice();
        this.sellType = placeOrderParam.getSellType();
        this.serviceContent=placeOrderParam.getServiceContent();
        this.isClaims=placeOrderParam.getIsClaims();
        this.workerOrMerchantUserId=placeOrderParam.getWorkerOrMerchantUserId();
    }
}
