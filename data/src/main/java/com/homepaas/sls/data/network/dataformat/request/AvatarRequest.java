package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sherily on 2016/9/18.
 */
public class AvatarRequest {

    /**
     * 待获取详情的对象 1 表示获取工人, 2 表示获取商户
     */
    @SerializedName("Type")
    private String type;

    /**
     * 待获取对象的标识
     */
    @SerializedName("Id")
    private String id;

    public AvatarRequest(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
