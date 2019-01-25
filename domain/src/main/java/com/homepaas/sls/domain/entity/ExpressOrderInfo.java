package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/7/7.
 * 跑腿实体类
 */

public class ExpressOrderInfo {
    // 目的地地址
    @SerializedName("TargetAddress")
    private ServiceAddressResponse targetAddress;

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
