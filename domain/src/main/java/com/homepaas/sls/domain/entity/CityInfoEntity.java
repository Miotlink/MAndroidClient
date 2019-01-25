package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sherily on 2017/7/21.
 */

public class CityInfoEntity {
    @SerializedName("FirstLetter")
    private String firstLetter;

    @SerializedName("Citys")
    private List<CityDetail> cityDetails;

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public List<CityDetail> getCityDetails() {
        return cityDetails;
    }

    public void setCityDetails(List<CityDetail> cityDetails) {
        this.cityDetails = cityDetails;
    }
}
