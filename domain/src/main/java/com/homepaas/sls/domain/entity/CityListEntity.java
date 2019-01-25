package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sherily on 2017/7/21.
 */

public class CityListEntity  {
    @SerializedName("CityInfos")
    private List<CityInfoEntity> cityInfoEntities;

    public List<CityInfoEntity> getCityInfoEntities() {
        return cityInfoEntities;
    }

    public void setCityInfoEntities(List<CityInfoEntity> cityInfoEntities) {
        this.cityInfoEntities = cityInfoEntities;
    }
}
