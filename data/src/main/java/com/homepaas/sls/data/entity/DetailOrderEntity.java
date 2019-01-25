package com.homepaas.sls.data.entity;

import android.renderscript.ScriptIntrinsicYuvToRGB;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.DetailOrder;
import com.homepaas.sls.domain.entity.Evaluation;

import java.util.List;

/**
 * Created by CMJ on 2016/6/23.
 */

public class DetailOrderEntity {
    @SerializedName("OrderId")
    public String orderId;
    @SerializedName("OrderCode")
    public String orderCode;
    @SerializedName("CreateTime")
    public String createTime;
    @SerializedName("OrderStatus")
    public String orderStatus;
    @SerializedName("AcceptTime")
    public String acceptTime;
    @SerializedName("CompleteTime")
    public String finishTime;
    @SerializedName("ConfirmTime")
    public String confirmTime;
    @SerializedName("CancelTime")
    public String cancelTime;
    @SerializedName("IsPayOff")
    public String isPayOff;
    @SerializedName("Price")
    public String price;
    @SerializedName("DiscountInfo")
    public String discountInfo;
    @SerializedName("PayLock")
    public String payLock;
    @SerializedName("ServiceProviderId")
    public String serviceProviderId;
    @SerializedName("ServiceProviderCollection")
    public String serviceProviderCollection;
    @SerializedName("ServiceProviderName")
    public String serviceProviderName;
    @SerializedName("ServiceProviderPic")
    public String serviceProvidePic;
    @SerializedName("ServiceProviderPraise")
    public String serviceProviderPraise;
    @SerializedName("ServiceProviderScore")
    public String serviceProviderScore;
    @SerializedName("ServiceProviderType")
    public String serviceProvideType;
    @SerializedName("Service")
    public OrderService service;
    @SerializedName("Evaluation")
    public Evaluation evaluation;
    @SerializedName("CanCancel")
    public String canCancel;
    @SerializedName("ServiceProviderPhoneNumber")
    public String serviceProviderPhoneNumber;
    @SerializedName("ServiceProviderCanCall")
    public String serviceProviderCanCall;
    @SerializedName("CustomerServicePhoneNumber")
    public String customerServicePhoneNumber;
    @SerializedName("Refunds")
    public List<Refund> refunds;

    public String getServiceProviderPhoneNumber() {
        return serviceProviderPhoneNumber;
    }

    public void setServiceProviderPhoneNumber(String serviceProviderPhoneNumber) {
        this.serviceProviderPhoneNumber = serviceProviderPhoneNumber;
    }

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

    public  String getOrderId() {
        return orderId;
    }

    public void setOrderId( String orderId) {
        this.orderId = orderId;
    }

    public  String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode( String orderCode) {
        this.orderCode = orderCode;
    }

    public  String getCreateTime() {
        return createTime;
    }

    public void setCreateTime( String createTime) {
        this.createTime = createTime;
    }

    public  String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus( String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public  String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime( String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public  String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime( String finishTime) {
        this.finishTime = finishTime;
    }

    public  String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime( String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public  String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime( String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public  String getIsPayOff() {
        return isPayOff;
    }

    public void setIsPayOff( String isPayOff) {
        this.isPayOff = isPayOff;
    }

    public  String getPrice() {
        return price;
    }

    public void setPrice( String price) {
        this.price = price;
    }

    public  String getDiscountInfo() {
        return discountInfo;
    }

    public void setDiscountInfo( String discountInfo) {
        this.discountInfo = discountInfo;
    }

    public  String getPayLock() {
        return payLock;
    }

    public void setPayLock( String payLock) {
        this.payLock = payLock;
    }

    public  String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId( String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public  String getServiceProviderCollection() {
        return serviceProviderCollection;
    }

    public void setServiceProviderCollection( String serviceProviderCollection) {
        this.serviceProviderCollection = serviceProviderCollection;
    }

    public  String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName( String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public  String getServiceProvidePic() {
        return serviceProvidePic;
    }

    public void setServiceProvidePic( String serviceProvidePic) {
        this.serviceProvidePic = serviceProvidePic;
    }

    public  String getServiceProviderPraise() {
        return serviceProviderPraise;
    }

    public void setServiceProviderPraise( String serviceProviderPraise) {
        this.serviceProviderPraise = serviceProviderPraise;
    }

    public  String getServiceProviderScore() {
        return serviceProviderScore;
    }

    public void setServiceProviderScore( String serviceProviderScore) {
        this.serviceProviderScore = serviceProviderScore;
    }

    public  String getServiceProvideType() {
        return serviceProvideType;
    }

    public void setServiceProvideType( String serviceProvideType) {
        this.serviceProvideType = serviceProvideType;
    }

    public String getCanCancel() {
        return canCancel;
    }

    public void setCanCancel(String canCancel) {
        this.canCancel = canCancel;
    }

    public List<Refund> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<Refund> refunds) {
        this.refunds = refunds;
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

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public static class OrderService {
        @SerializedName("ServiceId")
        public String serviceId;
        @SerializedName("ServiceName")
        public String serviceName;
        @SerializedName("Content")
        public String content;
        @SerializedName("Pictures")
        public List<Picture> pictures;
        @SerializedName("Price")
        public String price;
        @SerializedName("ServiceStartTime")
        public String serviceTime;

        public String getServiceTime() {
            return serviceTime;
        }

        public void setServiceTime(String serviceTime) {
            this.serviceTime = serviceTime;
        }

        public  String getServiceId() {
            return serviceId;
        }

        public void setServiceId( String serviceId) {
            this.serviceId = serviceId;
        }

        public  String getServiceName() {
            return serviceName;
        }

        public void setServiceName( String serviceName) {
            this.serviceName = serviceName;
        }

        public  String getContent() {
            return content;
        }

        public void setContent( String content) {
            this.content = content;
        }

        public  List<Picture> getPictures() {
            return pictures;
        }

        public void setPictures( List<Picture> pictures) {
            this.pictures = pictures;
        }

        public  String getPrice() {
            return price;
        }

        public void setPrice( String price) {
            this.price = price;
        }
    }

    public static class Evaluation {

        @SerializedName("Id")
        public String id;
        @SerializedName("ClientName")
        public String clientName;
        @SerializedName("ClientPic")
        public String clientPic;
        @SerializedName("Score")
        public String score;
        @SerializedName("Date")
        public String date;
        @SerializedName("Content")
        public String content;
        @SerializedName("Reply")
        public Reply reply;
        @SerializedName("IsCanBeAddtion")
        public String isCanBeAddtion;
        @SerializedName("AdditionalEvaluation")
        public AdditionalEvaluation additionalEvaluation;
        @SerializedName("Pictures")
        public List<Picture> pictures;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public String getClientPic() {
            return clientPic;
        }

        public void setClientPic(String clientPic) {
            this.clientPic = clientPic;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Reply getReply() {
            return reply;
        }

        public void setReply(Reply reply) {
            this.reply = reply;
        }

        public String getIsCanBeAddtion() {
            return isCanBeAddtion;
        }

        public void setIsCanBeAddtion(String isCanBeAddtion) {
            this.isCanBeAddtion = isCanBeAddtion;
        }

        public AdditionalEvaluation getAdditionalEvaluation() {
            return additionalEvaluation;
        }

        public void setAdditionalEvaluation(AdditionalEvaluation additionalEvaluation) {
            this.additionalEvaluation = additionalEvaluation;
        }

        public List<Picture> getPictures() {
            return pictures;
        }

        public void setPictures(List<Picture> pictures) {
            this.pictures = pictures;
        }
    }

    public static class AdditionalEvaluation {
        @SerializedName("Content")
        public String content;
        @SerializedName("Date")
        public String date;

        public  String getContent() {
            return content;
        }

        public void setContent( String content) {
            this.content = content;
        }

        public  String getDate() {
            return date;
        }

        public void setDate( String date) {
            this.date = date;
        }
    }

    public static class Reply {
        @SerializedName("Content")
        public String content;
        @SerializedName("Date")
        public String date;
    }

    public static class Picture {
        @SerializedName("PictureId")
        public String pictureId;
        @SerializedName("SmallPIc")
        public String smallPIc;
        @SerializedName("HqPic")
        public String hqPic;

        public  String getPictureId() {
            return pictureId;
        }

        public void setPictureId( String pictureId) {
            this.pictureId = pictureId;
        }

        public  String getSmallPIc() {
            return smallPIc;
        }

        public void setSmallPIc( String smallPIc) {
            this.smallPIc = smallPIc;
        }

        public  String getHqPic() {
            return hqPic;
        }

        public void setHqPic( String hqPic) {
            this.hqPic = hqPic;
        }
    }

    public static class Refund{
        @SerializedName("RefundTime")
        public String refundtime;
        @SerializedName("RefundAmount")
        public String refundAmount;
        @SerializedName("Status")
        public String status;
        @SerializedName("LostIncome")
        public String lostIncome;
        @SerializedName("OrderId")
        public String orderId;
        @SerializedName("UserId")
        public String userId;

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
