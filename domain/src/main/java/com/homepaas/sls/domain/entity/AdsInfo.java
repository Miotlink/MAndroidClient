package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sherily on 2016/12/27.
 */

public class AdsInfo {

    @SerializedName("Ads")
    private List<BannerInfo> bannerInfos;
    @SerializedName("Version")
    private String version;

    public List<BannerInfo> getBannerInfos() {
        return bannerInfos;
    }

    public void setBannerInfos(List<BannerInfo> bannerInfos) {
        this.bannerInfos = bannerInfos;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
