package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.inject.Singleton;

/**
 * Created by Administrator on 2016/7/9.
 */
public class PushInfo implements Serializable{
    //2是WebViewDetail，1是AppViewPage，3是OutterLink

    @SerializedName("Type")
    String jumpType;
    @SerializedName("SpId")
    String id;
    @SerializedName("SpType")
    String type;
    @SerializedName("AppViewId")
    String appViewId;
    @SerializedName("ServiceProvider")
    ServiceProvider serviceProvider;
    @SerializedName("Order")
    Order order;
    @SerializedName("Message")
    Message message;
    @SerializedName("Order2Pay")
    OrderPay orderToPay;
    @SerializedName("Worker")
    Worker worker;
    @SerializedName("Merchant")
    Merchant merchant;

    /*webview 需需要字段*/
    @SerializedName("Title")
    String title;
    @SerializedName("Url")
    String url;
    @SerializedName("IsShare")
    String isShare;
    @SerializedName("ShareUrl")
    String shareUrl;
    @SerializedName("ShareTitle")
    String shareTitle;
    @SerializedName("SharePic")
    String sharePic;
    @SerializedName("ShareDescription")
    String shareDescription;

    @SerializedName("Register")
    RecommendInfo recommendInfoRegister;
    @SerializedName("SetRecommend")
    RecommendInfo recommendInfoSetRecommend;
    private int shareResoures;

    //红包字段
    @SerializedName("NeedAlert")
    private String needAlert;
    @SerializedName("AlertTitle")
    private String alertTitle;
    @SerializedName("AlertMessage")
    private String alertMessage;
    @SerializedName("AlertCancelTitle")
    private String alertCancelTitle;
    @SerializedName("AlertConfirmTitle")
    private String alertConfirmTitle;

    //订单类型，0：普通，1：快递 2:跑腿
    @SerializedName("IsKdEOrder")
    private String isKdEOrder ;

    public String getIsKdEOrder() {
        return isKdEOrder;
    }

    public void setIsKdEOrder(String isKdEOrder) {
        this.isKdEOrder = isKdEOrder;
    }

    public String getAlertCancelTitle() {
        return alertCancelTitle;
    }

    public void setAlertCancelTitle(String alertCancelTitle) {
        this.alertCancelTitle = alertCancelTitle;
    }

    public String getAlertConfirmTitle() {
        return alertConfirmTitle;
    }

    public void setAlertConfirmTitle(String alertConfirmTitle) {
        this.alertConfirmTitle = alertConfirmTitle;
    }

    public String getNeedAlert() {
        return needAlert;
    }

    public void setNeedAlert(String needAlert) {
        this.needAlert = needAlert;
    }

    public String getAlertTitle() {
        return alertTitle;
    }

    public void setAlertTitle(String alertTitle) {
        this.alertTitle = alertTitle;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public RecommendInfo getRecommendInfoRegister() {
        return recommendInfoRegister;
    }

    public void setRecommendInfoRegister(RecommendInfo recommendInfoRegister) {
        this.recommendInfoRegister = recommendInfoRegister;
    }

    public RecommendInfo getRecommendInfoSetRecommend() {
        return recommendInfoSetRecommend;
    }

    public void setRecommendInfoSetRecommend(RecommendInfo recommendInfoSetRecommend) {
        this.recommendInfoSetRecommend = recommendInfoSetRecommend;
    }

    public String getShareDescription() {
        return shareDescription;
    }

    public void setShareDescription(String shareDescription) {
        this.shareDescription = shareDescription;
    }

    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsShare() {
        return isShare;
    }

    public void setIsShare(String isShare) {
        this.isShare = isShare;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getSharePic() {
        return sharePic;
    }

    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppViewId() {
        return appViewId;
    }

    public void setAppViewId(String appViewId) {
        this.appViewId = appViewId;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public OrderPay getOrderToPay() {
        return orderToPay;
    }

    public void setOrderToPay(OrderPay orderToPay) {
        this.orderToPay = orderToPay;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public void setShareResoures(int shareResoures) {
        this.shareResoures = shareResoures;
    }

    public int getShareResoures() {
        return shareResoures;
    }

    public static class  ServiceProvider implements Serializable{
        @SerializedName("SpId")
        String id;
        @SerializedName("SpType")
        String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Order implements Serializable{

        @SerializedName("OrderId")
        String orderId;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }

    public static class Message implements Serializable{
        @SerializedName("Id")
        String id;
        @SerializedName("MessageType")
        String messageType;
        @SerializedName("Title")
        String title;
        @SerializedName("Content")
        String content;
        @SerializedName("Date")
        String date;
        @SerializedName("JumpCode")
        String jumpCode;
        @SerializedName("JumpParam")
        String jumpParam;
        @SerializedName("IsRead")
        String isRead;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getJumpCode() {
            return jumpCode;
        }

        public void setJumpCode(String jumpCode) {
            this.jumpCode = jumpCode;
        }

        public String getJumpParam() {
            return jumpParam;
        }

        public void setJumpParam(String jumpParam) {
            this.jumpParam = jumpParam;
        }

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }
    }

    public static class OrderPay implements Serializable{//Order2Pay
        @SerializedName("OrderId")
        String orderId;
        @SerializedName("SpName")
        String serviceProviderName;
        @SerializedName("Price")
        String price;
        //商户支付
        @SerializedName("ServiceName")
        private String serviceName;

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getServiceProviderName() {
            return serviceProviderName;
        }

        public void setServiceProviderName(String serviceProviderName) {
            this.serviceProviderName = serviceProviderName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    public static class Worker implements Serializable{
        @SerializedName("WorkerId")
        String workerId;

        public String getWorkerId() {
            return workerId;
        }

        public void setWorkerId(String workerId) {
            this.workerId = workerId;
        }
    }
    public static class Merchant implements Serializable{
        @SerializedName("MerchantId")
        String merchantId;

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }
    }

    public static class RecommendInfo implements Serializable{
        @SerializedName("RecommendCode")
        String recommendCode;
        @SerializedName("RecommendUserId")
        String recommendUserId;
        @SerializedName("RecommendUserName")
        String recommendUserName;

        public String getRecommendCode() {
            return recommendCode;
        }

        public void setRecommendCode(String recommendCode) {
            this.recommendCode = recommendCode;
        }

        public String getRecommendUserId() {
            return recommendUserId;
        }

        public void setRecommendUserId(String recommendUserId) {
            this.recommendUserId = recommendUserId;
        }

        public String getRecommendUserName() {
            return recommendUserName;
        }

        public void setRecommendUserName(String recommendUserName) {
            this.recommendUserName = recommendUserName;
        }
    }
}
