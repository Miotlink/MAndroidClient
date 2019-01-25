package com.homepaas.sls.data.network.dataformat.request;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.UpdateCheck;

/**
 * on 2016/5/24 0024
 *
 * @author zhudongjie
 */
public class CheckUpdateBody {
    @SerializedName("UpdateInfo")
    public UpdateCheck update;
}
