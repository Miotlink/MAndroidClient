package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JWC on 2016/12/21.
 */

public class NewServiceType implements IService,Serializable{
    @SerializedName("Id")
    public String id;
    @SerializedName("Name")
    public String name;

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isWorker() {
        return false;
    }

    @Override
    public boolean isBusiness() {
        return false;
    }

    @Override
    public double getLng() {
        return 0;
    }

    @Override
    public double getLat() {
        return 0;
    }

    @Override
    public boolean isWholeCityProvider() {
        return false;
    }

    @Override
    public boolean isCommonBusinessWorker() {
        return false;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String getIconChecked() {
        return null;
    }

    @Override
    public ServiceType getDefService() {
        return null;
    }

    @Override
    public String getPhotoUrl() {
        return null;
    }


    @Override
    public int getServiceType() {
        return 0;
    }

    @Override
    public List<String> getServiceList() {
        return null;
    }
}
