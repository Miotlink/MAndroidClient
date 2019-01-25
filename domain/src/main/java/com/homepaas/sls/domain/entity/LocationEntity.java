package com.homepaas.sls.domain.entity;

import java.io.Serializable;

/**
 * Created by mhy on 2017/12/26.
 */

public class LocationEntity implements Serializable{

    private static final long serialVersionUID = -5217572630436226461L;
    private String address;
    private String city;
    private double longitude;//经度
    private double latitude;//维度

    public LocationEntity() {
    }

    public LocationEntity(String address, String city, double longitude, double latitude) {
        this.address = address;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
