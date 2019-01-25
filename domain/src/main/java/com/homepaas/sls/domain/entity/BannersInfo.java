package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sheirly on 2016/9/10.
 */
public class BannersInfo {
    private List<BannerInfo> bannerInfos;

    public List<BannerInfo> getBannerInfos() {
        return bannerInfos;
    }

    public void setBannerInfos(List<BannerInfo> bannerInfos) {
        this.bannerInfos = bannerInfos;
    }
}
