package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/6/20 0020
 *
 * @author zhudongjie
 */
public class BusinessServiceRequest {

    @SerializedName("MerchantId")
    public final String id;

    public BusinessServiceRequest(String id) {
        this.id = id;
    }
}
