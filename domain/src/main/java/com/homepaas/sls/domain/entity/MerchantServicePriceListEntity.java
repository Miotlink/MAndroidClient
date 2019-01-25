package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JWC on 2017/2/13.
 */

public class MerchantServicePriceListEntity {
    @SerializedName("ServiceTypeList")
    private List<MerchantServiceTypePrice> totalList;

    public List<MerchantServiceTypePrice> getTotalList() {
        return totalList;
    }

    public void setTotalList(List<MerchantServiceTypePrice> totalList) {
        this.totalList = totalList;
    }
}
