package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sheirly on 2016/9/9.
 */
public class GetBusinessTagsRequest {
    @SerializedName("BusinessId")
    public final String businessId;

    public GetBusinessTagsRequest(String businessId) {
        this.businessId = businessId;
    }
}
