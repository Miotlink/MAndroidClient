package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/2/14.
 */

public class WorkerResponse {
    @SerializedName("Id")
    private String id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Gender")
    private String gender;
    @SerializedName("Icon")
    private String icon;
    @SerializedName("PhoneNumber")
    private String phoneNumber;
    @SerializedName("CanCall")
    private String canCall;

    public String getCanCall() {
        return canCall;
    }

    public void setCanCall(String canCall) {
        this.canCall = canCall;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
