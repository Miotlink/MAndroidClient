package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/7/11 0011
 *
 * @author zhudongjie
 */
public class UploadQrCodeRequest {

    @SerializedName("Token")
    public final String token;

    @SerializedName("Url")
    public final String url;

    public UploadQrCodeRequest(String token, String url) {
        this.token = token;
        this.url = url;
    }
}
