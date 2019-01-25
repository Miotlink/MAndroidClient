package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2017/3/30.
 */

public class ServiceTimeStartAtEntry {
    @SerializedName("AvailableDate")
    private List<ServiceScheduleEntity> serviceScheduleEntities;

    public List<ServiceScheduleEntity> getServiceScheduleEntities() {
        return serviceScheduleEntities;
    }

    public void setServiceScheduleEntities(List<ServiceScheduleEntity> serviceScheduleEntities) {
        this.serviceScheduleEntities = serviceScheduleEntities;
    }
}
