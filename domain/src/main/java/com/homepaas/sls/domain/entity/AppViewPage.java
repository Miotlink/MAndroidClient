package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.PipedInputStream;
import java.io.Serializable;

/**
 * Created by Sherily on 2016/9/18.
 */
public class AppViewPage implements Serializable {

    /**
     * 透传消息类型	1
     */
    @SerializedName("Type")
    private String type;

    /**
     * 要跳转的App内页枚举值
     */
    @SerializedName("AppViewId")
    private String appViewId;

    /**
     *AppViewId=1009
     *AppViewId=10014
     */
    @SerializedName("ServiceProvider")
    private ServiceProvider serviceProvider;

    /**
     * AppViewId=10017
     *AppViewId=4
     */
    @SerializedName("Order")
    private Order order;

    /**
     * AppViewId=10021
     */
    @SerializedName("Message")
    private Message message;

    /**
     * AppViewId=10015
     */
    @SerializedName("Order2Pay")
    private Order2Pay order2Pay;

    /**
     * AppViewId=10018
     */
    @SerializedName("Worker")
    private Worker worker;

    /**
     * AppViewId=10019
     */
    @SerializedName("Merchant")
    private Merchant merchant;

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

    public static class ServiceProvider {

        /**
         * 工人商户Id
         */
        @SerializedName("SpId")
        private String spId;

        /**
         * 2:工人
         *3:商户
         */
        @SerializedName("SpType")
        private String spType;

        public String getSpId() {
            return spId;
        }

        public void setSpId(String spId) {
            this.spId = spId;
        }

        public String getSpType() {
            return spType;
        }

        public void setSpType(String spType) {
            this.spType = spType;
        }
    }

    public static class Order{

        /**
         * 订单Id
         */
        @SerializedName("OrderId")
        private String orderId;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
    }

    public static class Message{

        @SerializedName("Id")
        private String id;

        @SerializedName("MessageType")
        private String messageType;

        @SerializedName("Title")
        private String title;

        @SerializedName("Content")
        private String content;

        @SerializedName("Date")
        private String date;

        @SerializedName("JumpCode")
        private String jumpCode;

        @SerializedName("JumpParam")
        private String jumpParam;

        @SerializedName("IsRead")
        private String isRead;

        public boolean isRead(){
            return isRead.equals("1");
        }

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

    public static class Order2Pay{

        /**
         * 订单Id
         */
        @SerializedName("OrderId")
        private String orderId;

        /**
         * 工人/商户名字
         */
        @SerializedName("SpName")
        private String spName;

        /**
         * 价格
         */
        @SerializedName("Price")
        private String price;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getSpName() {
            return spName;
        }

        public void setSpName(String spName) {
            this.spName = spName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    public static class Worker{

        @SerializedName("WorkerId")
        private String workerId;

        public String getWorkerId() {
            return workerId;
        }

        public void setWorkerId(String workerId) {
            this.workerId = workerId;
        }
    }

    public static class Merchant{

        @SerializedName("MerchantId")
        private String merchantId;

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }
    }
}
