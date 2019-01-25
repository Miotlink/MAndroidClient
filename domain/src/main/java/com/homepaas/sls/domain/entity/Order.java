package com.homepaas.sls.domain.entity;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/5/3.
 */
public class Order {

    String orderId;
    String orderCode;
    String createTime;
    String orderStatus;
    String isPayOff;
    String price;
    String discountInfo;
    String payLock;
    String serviceProviderId;
    String serviceProvideType;
    String serviceProvidePic;
    String serviceProviderName;
    String isEvaluated;
    OrderService service;

/*    int[] statusCode   = {0,1,2,4,5};
    int[] serviceType = {2,3};
    static String[] names = {"Alice","Bod","HomeJoy","HomePass","Qin","Hiray"};
    static DecimalFormat decimalFormat = new DecimalFormat("0.00");*/
    public Order() {
        /*this.orderId = String.valueOf(System.currentTimeMillis());
        this.orderCode = String.valueOf(Math.random()*1000000);
        this.orderStatus = String.valueOf(statusCode[new Random().nextInt(5)]);
        this.serviceProviderId = String.valueOf(System.currentTimeMillis());
        this.serviceProvideType = String.valueOf(serviceType[new Random().nextInt(2)]);
        this.serviceProviderName = String.valueOf(names[new Random().nextInt(6)]);
        this.serviceProvidePic="http://p4.so.qhimg.com/bdr/_240_/t01d4df9bbb2e43ff44.jpg";
        this.isPayOff = Math.random()>1?1+"":0+"";
        this.price = decimalFormat.format(Math.random()*1000);
        this.service = new OrderService();*/
    }

    public String getIsEvaluated() {
        return isEvaluated;
    }

    public void setIsEvaluated(String isEvaluated) {
        this.isEvaluated = isEvaluated;
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

    public void setPayLock(String payLock) {
        this.payLock = payLock;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getServiceProvidePic() {
        return serviceProvidePic;
    }

    public void setServiceProvidePic(String serviceProvidePic) {
        this.serviceProvidePic = serviceProvidePic;
    }


    public String getServiceProviderName() {
        return serviceProviderName;
    }

    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    public String getServiceProvideType() {
        return serviceProvideType;
    }

    public void setServiceProvideType(String serviceProvideType) {
        this.serviceProvideType = serviceProvideType;
    }

    public OrderService getService() {
        return service;
    }

    public void setService(OrderService service) {
        this.service = service;
    }

    public static  class OrderService{
        String serviceId;
        String serviceName;
        String content;
        List<Picture> pictures;

        public OrderService() {
          /*  this.serviceId = String.valueOf(System.currentTimeMillis());
            this.serviceName = String.valueOf(names[new Random().nextInt(names.length)]);
            this.content="i want to get an aunt for home stuff ,best to be HangZhou native people .remember it !!!";*/
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

        public static class Picture{
            String pictureId;
            String smallPic;
            String hqPic;

            public String getPictureId() {
                return pictureId;
            }

            public void setPictureId(String pictureId) {
                this.pictureId = pictureId;
            }

            public String getSmallPic() {
                return smallPic;
            }

            public void setSmallPic(String smallPic) {
                this.smallPic = smallPic;
            }

            public String getHqPic() {
                return hqPic;
            }

            public void setHqPic(String hqPic) {
                this.hqPic = hqPic;
            }
        }
    }
}
