package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/6/5.
 * 快递详情，订单不是快递时，为空
 */

public class KdEOrderInfo {
    // 1:现付，2到付
    @SerializedName("PayType")
    private String payType;
    // 预估费用
    @SerializedName("PerPayAmount")
    private String perPayAmount;
    // 快递公司编号
    @SerializedName("ShipperCode")
    private String shipperCode;
    // 快递公司名称
    @SerializedName("ShipperName")
    private String shipperName;
    //公司icon
    @SerializedName("ShipperIcon")
    private String shipperIcon;
    // 物流编号
    @SerializedName("LogisticCode")
    private String logisticCode;
    // 物品名字
    @SerializedName("GoodsName")
    private String goodsName;
    // 物品重量
    @SerializedName("GoodsWight")
    private String goodsWight;
    // 目的地地址
    @SerializedName("TargetAddress")
    private ServiceAddressResponse targetAddress;

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPerPayAmount() {
        return perPayAmount;
    }

    public void setPerPayAmount(String perPayAmount) {
        this.perPayAmount = perPayAmount;
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

    public String getShipperIcon() {
        return shipperIcon;
    }

    public void setShipperIcon(String shipperIcon) {
        this.shipperIcon = shipperIcon;
    }

    public String getLogisticCode() {
        return logisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        this.logisticCode = logisticCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsWight() {
        return goodsWight;
    }

    public void setGoodsWight(String goodsWight) {
        this.goodsWight = goodsWight;
    }

    public ServiceAddressResponse getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(ServiceAddressResponse targetAddress) {
        this.targetAddress = targetAddress;
    }

    public static class ServiceAddressResponse {
        // 联系人
        @SerializedName("Contact")
        private String contact;
        // 0 先生/1 女士
        @SerializedName("Gender")
        private String gender;
        // 联系电话
        @SerializedName("PhoneNumber")
        private String phoneNumber;
        // 服务地址
        @SerializedName("Address1")
        private String address1;
        //服务详细地址
        @SerializedName("Address2")
        private String address2;
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
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
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
