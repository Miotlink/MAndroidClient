package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2017/6/5.
 */

public class GetKdTrackInfoRequest {
    //物流单号
    @SerializedName("LogisticCode")
    private String logisticCode;
    //物流公司编码
    @SerializedName("ShipperCode")
    private String shipperCode;

    public GetKdTrackInfoRequest(String logisticCode, String shipperCode) {
        this.logisticCode = logisticCode;
        this.shipperCode = shipperCode;
    }
}
