package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.data.entity.BusinessEntity;

/**
 * on 2016/1/13 0013
 *
 * @author zhudongjie .
 */
public class BusinessInfoBody {

    @SerializedName("Business")
    private BusinessEntity business;

    public BusinessEntity getBusiness() {
        return business;
    }
}
