package com.homepaas.sls.domain.entity.embedded_class;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CJJ on 2016/9/16.
 */

public class Address implements Serializable{

    @SerializedName("Contact")
    public String contact;
    @SerializedName("Gender")
    public String gender;
    @SerializedName("PhoneNumber")
    public String phoneNumber;
    @SerializedName("Address1")
    public String address1;
    @SerializedName("Address2")
    public String address2;
    @SerializedName("Address1Lat")
    public String address1Lat;
    @SerializedName("Address1Lng")
    public String address1Lng;

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
