package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2017/5/26.
 */

public class CommonCouponInfo {

    @SerializedName("CouponList")
    private List<CommonCouponEntity> commonCouponEntityList;

    public List<CommonCouponEntity> getCommonCouponEntityList() {
        return commonCouponEntityList;
    }

    public void setCommonCouponEntityList(List<CommonCouponEntity> commonCouponEntityList) {
        this.commonCouponEntityList = commonCouponEntityList;
    }
}
