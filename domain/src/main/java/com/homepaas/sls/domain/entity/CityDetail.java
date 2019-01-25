package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sherily on 2017/7/21.
 */

public class CityDetail extends BaseIndexPinyinBean implements Serializable{
    @SerializedName("CityName")
    private String cityName;
    @SerializedName("CityLng")
    private String cityLng;
    @SerializedName("CityLat")
    private String cityLat;
    @SerializedName("Code")
    private String code;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityLng() {
        return cityLng;
    }

    public void setCityLng(String cityLng) {
        this.cityLng = cityLng;
    }

    public String getCityLat() {
        return cityLat;
    }

    public void setCityLat(String cityLat) {
        this.cityLat = cityLat;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTarget() {
        return cityName;
    }
}
