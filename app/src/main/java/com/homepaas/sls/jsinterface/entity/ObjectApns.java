package com.homepaas.sls.jsinterface.entity;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.LoginBody;
import com.homepaas.sls.domain.entity.ServiceItem;

import java.io.Serializable;

/**
 * Created by JWC on 2017/6/1.
 * 通用App和H5交互
 */

public class ObjectApns implements Serializable{
    //0 默认，信息 //1 内链 //2 跳转新web //3 此web刷新新url
    @SerializedName("Type")
    private String type;
    //2或者3类型跳转的url
    @SerializedName("JumpUrl")
    private String jumpUrl;
    //WebAppViewId枚举 比如"1000"就弹一个登陆弹窗
    @SerializedName("AppViewId")
    private String appViewId;
    @SerializedName("ContentAll")
    private ContentAll contentAll;
    //是否需要弹窗  0不需要  1需要
    @SerializedName("NeedAlert")
    private String needAlert;
    //弹窗title
    @SerializedName("AlertTitle")
    private String alertTitle;
    //弹窗msg
    @SerializedName("AlertMessage")
    private String alertMessage;
    //弹窗取消按钮
    @SerializedName("AlertCancelTitle")
    private String alertCancelTitle;
    //弹窗确定按钮
    @SerializedName("AlertConfirmTitle")
    private String alertConfirmTitle;
    //是否是活动
    @SerializedName("IsActivity")
    private String isActivity;
    //如果跳转订单详情  需要这里传orderid
    @SerializedName("Order")
    private Order order;
    //下单传递数据
    @SerializedName("ServiceOrder")
    private ServiceOrder serviceOrder;
    //如果跳转消息，需要附带消息的信息
    @SerializedName("Message")
    private Message message;
    //跳转付款
    @SerializedName("Order2Pay")
    private Order2Pay order2Pay;
    //跳转工人详情
    @SerializedName("Worker")
    private Worker worker;
    //跳转商户详情
    @SerializedName("Merchant")
    private Merchant merchant;
    //分享
    @SerializedName("ShareInfo")
    private ShareInfo shareInfo;
    //注册并填写邀请码，如果不需要邀请码  不传这个
    @SerializedName("Register")
    private Register register;
    //跳转设置界面填写邀请码
    @SerializedName("SetRecommend")
    private SetRecommend setRecommend;

    @SerializedName("DisCountInfo")
    private DisCountInfo disCountInfo;

    @SerializedName("CustomerService")
    public CustomerService customerService;

    @SerializedName("DirectOrderCommand")
    private DirectOrderCommand directOrderCommand;

    @SerializedName("CouponServiceItem")
    private ServiceItem serviceItem;
    //需要进行后台登录的参数，AppViewId: 998
    @SerializedName("LoginUserInfo")
    private LoginBody loginBody;

    public LoginBody getLoginBody() {
        return loginBody;
    }

    public void setLoginBody(LoginBody loginBody) {
        this.loginBody = loginBody;
    }

    public ServiceItem getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(ServiceItem serviceItem) {
        this.serviceItem = serviceItem;
    }

    public ContentAll getContentAll() {
        return contentAll;
    }

    public void setContentAll(ContentAll contentAll) {
        this.contentAll = contentAll;
    }

    public DirectOrderCommand getDirectOrderCommand() {
        return directOrderCommand;
    }

    public void setDirectOrderCommand(DirectOrderCommand directOrderCommand) {
        this.directOrderCommand = directOrderCommand;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public DisCountInfo getDisCountInfo() {
        return disCountInfo;
    }

    public void setDisCountInfo(DisCountInfo disCountInfo) {
        this.disCountInfo = disCountInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getAppViewId() {
        return appViewId;
    }

    public void setAppViewId(String appViewId) {
        this.appViewId = appViewId;
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

    public String getIsActivity() {
        return isActivity;
    }

    public void setIsActivity(String isActivity) {
        this.isActivity = isActivity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }

    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Order2Pay getOrder2Pay() {
        return order2Pay;
    }

    public void setOrder2Pay(Order2Pay order2Pay) {
        this.order2Pay = order2Pay;
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

    public ShareInfo getShareInfo() {
        return shareInfo;
    }

    public void setShareInfo(ShareInfo shareInfo) {
        this.shareInfo = shareInfo;
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public SetRecommend getSetRecommend() {
        return setRecommend;
    }

    public void setSetRecommend(SetRecommend setRecommend) {
        this.setRecommend = setRecommend;
    }

    public static class ContentAll implements Serializable{
        @SerializedName("Content")
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class Order implements Serializable{
        @SerializedName("OrderId")
        private String orderId;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

    }

    public static class ServiceOrder implements Serializable{
        @SerializedName("ServiceId")
        private String serviceId;
        @SerializedName("ServiceName")
        private String serviceName;
        @SerializedName("IsActivityHandle")
        private String isActivityHandle;

        public String getIsActivityHandle() {
            return isActivityHandle;
        }

        public void setIsActivityHandle(String isActivityHandle) {
            this.isActivityHandle = isActivityHandle;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }
    }

    public static class Message implements Serializable{
        @SerializedName("Id")
        private String id;
        @SerializedName("Title")
        private String title;
        @SerializedName("Content")
        private String content;
        @SerializedName("Date")
        private String date;

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
    }

    public static class Order2Pay implements Serializable {
        @SerializedName("OrderId")
        private String orderId;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }

    public static class Worker implements Serializable{
        @SerializedName("WorkerId")
        private String workerId;

        public String getWorkerId() {
            return workerId;
        }

        public void setWorkerId(String workerId) {
            this.workerId = workerId;
        }
    }

    public static class Merchant implements Serializable{
        @SerializedName("MerchantId")
        private String merchantId;

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }
    }

    public static class ShareInfo implements Serializable{
        @SerializedName("Title")
        private String title;
        @SerializedName("Description")
        private String description;
        @SerializedName("Url")
        private String url;
        @SerializedName("Picture")
        private String picture;
        @SerializedName("ContentType")
        private String contentType;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }
    }

    public static class Register implements Serializable{
        @SerializedName("RecommendUserId")
        private String recommendUserId;
        @SerializedName("RecommendCode")
        private String recommendCode;
        @SerializedName("RecommendUserName")
        private String recommendUserName;

        public String getRecommendUserId() {
            return recommendUserId;
        }

        public void setRecommendUserId(String recommendUserId) {
            this.recommendUserId = recommendUserId;
        }

        public String getRecommendCode() {
            return recommendCode;
        }

        public void setRecommendCode(String recommendCode) {
            this.recommendCode = recommendCode;
        }

        public String getRecommendUserName() {
            return recommendUserName;
        }

        public void setRecommendUserName(String recommendUserName) {
            this.recommendUserName = recommendUserName;
        }
    }

    public static class SetRecommend implements Serializable{
        @SerializedName("RecommendUserId")
        private String recommendUserId;
        @SerializedName("RecommendCode")
        private String recommendCode;
        @SerializedName("RecommendUserName")
        private String recommendUserName;

        public String getRecommendUserId() {
            return recommendUserId;
        }

        public void setRecommendUserId(String recommendUserId) {
            this.recommendUserId = recommendUserId;
        }

        public String getRecommendCode() {
            return recommendCode;
        }

        public void setRecommendCode(String recommendCode) {
            this.recommendCode = recommendCode;
        }

        public String getRecommendUserName() {
            return recommendUserName;
        }

        public void setRecommendUserName(String recommendUserName) {
            this.recommendUserName = recommendUserName;
        }
    }
}
