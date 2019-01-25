package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2017/7/19.
 */

public class TrackOrderInfo {
    /**
     * CountDown : string
     * Status : string
     * Description : string
     * OrderProviderInfo : YJ.Caller.ClientApi.Models.Order.OrderProviderInfo
     */


    @SerializedName("DateStatus")
    private List<DateStatus> dateStatus;
    @SerializedName("NextStatus")
    private NextStatus nextStatus;

    public List<DateStatus> getDateStatus() {
        return dateStatus;
    }

    public void setDateStatus(List<DateStatus> dateStatus) {
        this.dateStatus = dateStatus;
    }

    public NextStatus getNextStatus() {
        return nextStatus;
    }

    public void setNextStatus(NextStatus nextStatus) {
        this.nextStatus = nextStatus;
    }


    public static class DateStatus {

        @SerializedName("Date")
        private String date;
        @SerializedName("Status")
        List<OrderStatusInfo> orderStatusInfoList;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        //
        public List<OrderStatusInfo> getOrderStatusInfoList() {
            return orderStatusInfoList;
        }

        public void setOrderStatusInfoList(List<OrderStatusInfo> orderStatusInfoList) {
            this.orderStatusInfoList = orderStatusInfoList;
        }
    }

    public static class OrderStatusInfo {
        private int type;//item布局类型
        public static final int COUNT_TIME = 100;//倒计时item布局标识
        public static final int DEFATULT_STATUS = 200;//其他默认布局
        public static final int TIME_HEAD = 300;//每天的头item
        private String headItemData;

        //日期 如 06-23,
        @SerializedName("Date")
        private String date;
        //时间 如 18：00,
        @SerializedName("Time")
        private String time;
        //订单状态 如 下单成功,
        @SerializedName("Status")
        private String status;
        //状态描述,
        @SerializedName("Description")
        private String description;
        //订单 服务提供者的信息，如果没有 则为空
        @SerializedName("OrderProviderInfo")
        private OrderProviderInfo orderProviderInfo;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getHeadItemData() {
            return headItemData;
        }

        public void setHeadItemData(String headItemData) {
            this.headItemData = headItemData;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public OrderProviderInfo getOrderProviderInfo() {
            return orderProviderInfo;
        }

        public void setOrderProviderInfo(OrderProviderInfo orderProviderInfo) {
            this.orderProviderInfo = orderProviderInfo;
        }

        public class OrderProviderInfo {
            //名字,
            @SerializedName("Name")
            private String name;
            //头像
            @SerializedName("HeadPortrait")
            private String headPortrait;
            //电话号码
            @SerializedName("PhoneNumber")
            private String phoneNumber;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHeadPortrait() {
                return headPortrait;
            }

            public void setHeadPortrait(String headPortrait) {
                this.headPortrait = headPortrait;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }
        }
    }

    public class NextStatus {
//
//        CountDown (string, optional): 倒计时，秒数 可为Null，如果为Null，表示没有倒计时,
//        Status (string, optional): 订单状态 如 下单成功,
//        Description (string, optional): 状态描述,
//        OrderProviderInfo (YJ.Caller.ClientApi.Models.Order.OrderProviderInfo, optional): 订单 服务提供者的信息，如果没有 则为空

        private String CountDown;
        private String Status;
        private String Description;
        @SerializedName("OrderProviderInfo")
        private OrderStatusInfo.OrderProviderInfo orderProviderInfo;

        public String getCountDown() {
            return CountDown;
        }

        public void setCountDown(String countDown) {
            CountDown = countDown;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public OrderStatusInfo.OrderProviderInfo getOrderProviderInfo() {
            return orderProviderInfo;
        }

        public void setOrderProviderInfo(OrderStatusInfo.OrderProviderInfo orderProviderInfo) {
            this.orderProviderInfo = orderProviderInfo;
        }
    }

}
