package com.homepaas.sls.mvp.model;

import com.homepaas.sls.domain.entity.AdsInfo;
import com.homepaas.sls.domain.entity.RecommendServiceEntity;
import com.homepaas.sls.domain.entity.ShortCutEntity;

/**
 * Created by mhy on 2017/8/16.
 */

public class HomeListData {
    // 轮播图
    private AdsInfo adsInfo;
    // 分类
    private ShortCutEntity ShortCutEntity;
    // 服务列表
    private RecommendServiceEntity RecommendServiceEntity;

    public AdsInfo getAdsInfo() {
        return adsInfo;
    }

    public void setAdsInfo(AdsInfo adsInfo) {
        this.adsInfo = adsInfo;
    }

    public com.homepaas.sls.domain.entity.ShortCutEntity getShortCutEntity() {
        return ShortCutEntity;
    }

    public void setShortCutEntity(com.homepaas.sls.domain.entity.ShortCutEntity shortCutEntity) {
        ShortCutEntity = shortCutEntity;
    }

    public com.homepaas.sls.domain.entity.RecommendServiceEntity getRecommendServiceEntity() {
        return RecommendServiceEntity;
    }

    public void setRecommendServiceEntity(com.homepaas.sls.domain.entity.RecommendServiceEntity recommendServiceEntity) {
        RecommendServiceEntity = recommendServiceEntity;
    }
}
