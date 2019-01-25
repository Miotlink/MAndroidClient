package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/12/21.
 * 响应数据包装类
 */

public class ResponseWrapper<T> {
    @SerializedName("Meta")
    public Meta meta;
    @SerializedName("Body")
    public T data;
}
