package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2017/7/6.
 * 快递类 跑腿服务
 */

public class ExpressDetailOutputEntity {
    // 订单编号
    @SerializedName("OrderCode")
    private String orderCode;
    //第三方物流编号,
    @SerializedName("LogisticCode")
    private String logisticCode;
    //快递公司编码,
    @SerializedName("ShipperCode")
    private String shipperCode;
    // 快递公司名称,
    @SerializedName("ShipperName")
    private String shipperName;
    // 是否成功,
    @SerializedName("Success")
    private String success;
    //成功,
    @SerializedName("Reason")
    private String reason;
    //只有10的时候，才是已完成，右上角更多改为客服
    @SerializedName("State")
    private String state;
    //名称,
    @SerializedName("GoodsName")
    private String goodsName;
    //取消原因
    @SerializedName("CancelReason")
    private String cancelReason;
    //发货时间
    @SerializedName("ServiceTime")
    private String serviceTime;
    //重量,
    @SerializedName("GoodsWeight")
    private String goodsWeight;
    //备注
    @SerializedName("Note")
    private String note;
    //费用,
    @SerializedName("Price")
    private String price;
    // 跑男,
    @SerializedName("DriverName")
    private String driverName;
    //跑男手机,
    @SerializedName("DriverPhone")
    private String driverPhone;
    //创建时间,
    @SerializedName("CreateTime")
    private String createTime;
    // 寄件人,
    @SerializedName("SenderAddress")
    private ServiceAddressResponse senderAddress;
    //收件人,
    @SerializedName("RevicerAddress")
    private ServiceAddressResponse revicerAddress;
    //物流跟踪信息
    @SerializedName("Traces")
    private List<KdApiSearchResponseTrace> traces;
    //取消订单页面的文字
    @SerializedName("CancelMsg")
    private String cancelMsg;
    //是否支付过 0：没有 1：有
    @SerializedName("IsPayOff")
    public String isPayOff;
    // 订单按钮的显示
    @SerializedName("OrderBtnInfo")
    private OrderBtnInfo orderBtnInfo;

    public OrderBtnInfo getOrderBtnInfo() {
        return orderBtnInfo;
    }

    public void setOrderBtnInfo(OrderBtnInfo orderBtnInfo) {
        this.orderBtnInfo = orderBtnInfo;
    }

    public String getIsPayOff() {
        return isPayOff;
    }

    public void setIsPayOff(String isPayOff) {
        this.isPayOff = isPayOff;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getLogisticCode() {
        return logisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        this.logisticCode = logisticCode;
    }

    public String getShipperCode() {
        return shipperCode;
    }

    public void setShipperCode(String shipperCode) {
        this.shipperCode = shipperCode;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(String goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ServiceAddressResponse getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(ServiceAddressResponse senderAddress) {
        this.senderAddress = senderAddress;
    }

    public ServiceAddressResponse getRevicerAddress() {
        return revicerAddress;
    }

    public void setRevicerAddress(ServiceAddressResponse revicerAddress) {
        this.revicerAddress = revicerAddress;
    }

    public List<KdApiSearchResponseTrace> getTraces() {
        return traces;
    }

    public void setTraces(List<KdApiSearchResponseTrace> traces) {
        this.traces = traces;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getCancelMsg() {
        return cancelMsg;
    }

    public void setCancelMsg(String cancelMsg) {
        this.cancelMsg = cancelMsg;
    }

    public class ServiceAddressResponse {
        //联系
        @SerializedName("Contact")
        private String contact;
        // 0 先生/1 女士,
        @SerializedName("Gender")
        private String gender;
        //联系电话
        @SerializedName("PhoneNumber")
        private String phoneNumber;
        //服务地址
        @SerializedName("Address1")
        private String address1;
        //服务详细地址
        @SerializedName("Address2")
        private String Address2;
        //服务纬度
        @SerializedName("Address1Lat")
        private String address1Lat;
        //服务经度
        @SerializedName("Address1Lng")
        private String address1Lng;

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

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return Address2;
        }

        public void setAddress2(String address2) {
            Address2 = address2;
        }

        public String getAddress1Lat() {
            return address1Lat;
        }

        public void setAddress1Lat(String address1Lat) {
            this.address1Lat = address1Lat;
        }

        public String getAddress1Lng() {
            return address1Lng;
        }

        public void setAddress1Lng(String address1Lng) {
            this.address1Lng = address1Lng;
        }
    }
}
