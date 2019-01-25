package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Random;

/**
 * Created by CMJ on 2016/6/23.
 */

public class DetailOrder {

    String orderId;
    String orderCode;
    String createTime;
    String orderStatus;
    String acceptTime;
    String finishTime;
    String confirmTime;
    String cancelTime;
    String isPayOff;
    String price;
    String discountInfo;
    String payLock;
    String serviceProviderId;
    String serviceProviderCollection;
    String serviceProviderName;
    String serviceProvidePic;
    String serviceProviderPraise;
    String serviceProviderScore;
    String serviceProvideType;
    String serviceProviderPhoneNumber;
    String canCancel;
    String clientPhone;
    String customerServicePhoneNumber;
    String serviceProviderCanCall;
    OrderService service;
    Evaluation evaluation;
    List<Refund> refunds;

    public String getServiceProviderCanCall() {
        return serviceProviderCanCall;
    }

    public void setServiceProviderCanCall(String serviceProviderCanCall) {
        this.serviceProviderCanCall = serviceProviderCanCall;
    }

    public String getCustomerServicePhoneNumber() {
        return customerServicePhoneNumber;
    }

    public void setCustomerServicePhoneNumber(String customerServicePhoneNumber) {
        this.customerServicePhoneNumber = customerServicePhoneNumber;
    }

    public String getServiceProviderPhoneNumber() {
        return serviceProviderPhoneNumber;
    }

    public void setServiceProviderPhoneNumber(String serviceProviderPhoneNumber) {
        this.serviceProviderPhoneNumber = serviceProviderPhoneNumber;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getIsPayOff() {
        return isPayOff;
    }

    public void setIsPayOff(String isPayOff) {
        this.isPayOff = isPayOff;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscountInfo() {
        return discountInfo;
    }

    public void setDiscountInfo(String discountInfo) {
        this.discountInfo = discountInfo;
    }

    public String getPayLock() {
        return payLock;
    }

    public String getCanCancel() {
        return canCancel;
    }

    public void setCanCancel(String canCancel) {
        this.canCancel = canCancel;
    }

    public void setPayLock(String payLock) {
        this.payLock = payLock;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getServiceProviderCollection() {
        return serviceProviderCollection;
    }

    public void setServiceProviderCollection(String serviceProviderCollection) {
        this.serviceProviderCollection = serviceProviderCollection;
    }

    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public String getServiceProvidePic() {
        return serviceProvidePic;
    }

    public void setServiceProvidePic(String serviceProvidePic) {
        this.serviceProvidePic = serviceProvidePic;
    }

    public String getServiceProviderPraise() {
        return serviceProviderPraise;
    }

    public void setServiceProviderPraise(String serviceProviderPraise) {
        this.serviceProviderPraise = serviceProviderPraise;
    }

    public String getServiceProviderScore() {
        return serviceProviderScore;
    }

    public void setServiceProviderScore(String serviceProviderScore) {
        this.serviceProviderScore = serviceProviderScore;
    }

    public String getServiceProvideType() {
        return serviceProvideType;
    }

    public void setServiceProvideType(String serviceProvideType) {
        this.serviceProvideType = serviceProvideType;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public OrderService getService() {
        return service;
    }

    public void setService(OrderService service) {
        this.service = service;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public List<Refund> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<Refund> refunds) {
        this.refunds = refunds;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public Refund getUpdateRefund(){
        Refund updateRefund= null;
        if (refunds != null){
            int validIndex = refunds.isEmpty()?-1:refunds.size()-1;
            if (validIndex !=-1)
            updateRefund = refunds.get(validIndex);
        }
        return updateRefund;

    }

    public static class OrderService {
        String serviceId;
        String serviceName;
        String content;
        List<Picture> pictures;
        String price;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<Picture> getPictures() {
            return pictures;
        }

        public void setPictures(List<Picture> pictures) {
            this.pictures = pictures;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    public static class Evaluation {

        public  String Id;
        public  String ClientName;
        public String ClientPic;
        public String Score;
        public String Date;
        public String Content;
        public Reply Reply;
        public String IsCanBeAddtion;
        public AdditionalEvaluation AdditionalEvaluation;
        public List<Picture> Pictures;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getClientName() {
            return ClientName;
        }

        public void setClientName(String clientName) {
            ClientName = clientName;
        }

        public String getClientPic() {
            return ClientPic;
        }

        public void setClientPic(String clientPic) {
            ClientPic = clientPic;
        }

        public String getScore() {
            return Score;
        }

        public void setScore(String score) {
            Score = score;
        }

        public String getDate() {
            return Date;
        }

        public void setDate(String date) {
            Date = date;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }

        public Reply getReply() {
            return Reply;
        }

        public void setReply(Reply reply) {
            Reply = reply;
        }

        public String getIsCanBeAddtion() {
            return IsCanBeAddtion;
        }

        public void setIsCanBeAddtion(String isCanBeAddtion) {
            IsCanBeAddtion = isCanBeAddtion;
        }

        public AdditionalEvaluation getAdditionalEvaluation() {
            return AdditionalEvaluation;
        }

        public void setAdditionalEvaluation(AdditionalEvaluation additionalEvaluation) {
            AdditionalEvaluation = additionalEvaluation;
        }

        public List<Picture> getPictures() {
            return Pictures;
        }

        public void setPictures(List<Picture> pictures) {
            Pictures = pictures;
        }
    }

    public static class AdditionalEvaluation {
        String content;
        String date;

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

    public static class Reply {
        String content;
        String date;

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

    public static class Picture {
        String pictureId;
        String smallPIc;
        String hqPic;

        public String getPictureId() {
            return pictureId;
        }

        public void setPictureId(String pictureId) {
            this.pictureId = pictureId;
        }

        public String getSmallPIc() {
            return smallPIc;
        }

        public void setSmallPIc(String smallPIc) {
            this.smallPIc = smallPIc;
        }

        public String getHqPic() {
            return hqPic;
        }

        public void setHqPic(String hqPic) {
            this.hqPic = hqPic;
        }
    }

    public static class Refund{
        public String refundtime;
        public String refundAmount;
        public String lostIncome;
        public String orderId;
        public String userId;
        public String status;

        public String getRefundtime() {
            return refundtime;
        }

        public void setRefundtime(String refundtime) {
            this.refundtime = refundtime;
        }

        public String getRefundAmount() {
            return refundAmount;
        }

        public void setRefundAmount(String refundAmount) {
            this.refundAmount = refundAmount;
        }

        public String getLostIncome() {
            return lostIncome;
        }

        public void setLostIncome(String lostIncome) {
            this.lostIncome = lostIncome;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
