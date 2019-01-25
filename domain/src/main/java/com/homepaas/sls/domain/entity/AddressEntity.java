package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CJJ on 2016/9/13.
 * 客户服务地址
 */

public class AddressEntity implements Serializable{
    @SerializedName("Id")
    public String id;
    @SerializedName("Contact")
    public String contact;
    @SerializedName("Gender")
    public String gender;
    @SerializedName("PhoneNumber")
    public String phoneNumber;
    @SerializedName("Tag")
    public String tag;
    @SerializedName("Address1")
    public String address;
    @SerializedName("Address1Lat")
    public String lat;
    @SerializedName("Address1Lng")
    public String lng;
    @SerializedName("Address2")
    public String detailAddress;

    @SerializedName("AppViewId")
    private String appViewId;
    @SerializedName("Token")//不是接口传值
    private String token;//H5选择地址的时候完后创建订单需要token

    public String getAppViewId() {
        return appViewId;
    }

    public void setAppViewId(String appViewId) {
        this.appViewId = appViewId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AddressEntity() {
    }

    public AddressEntity(String id, String contact, String gender, String phoneNumber, String tag, String address, String lat, String lng, String detailAddress) {
        this.id = id;
        this.contact = contact;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.tag = tag;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.detailAddress = detailAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
}
