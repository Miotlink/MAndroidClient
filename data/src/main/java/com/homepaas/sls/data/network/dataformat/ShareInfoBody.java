package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.ShareInfo;

/**
 * on 2016/7/1 0001
 *
 * @author zhudongjie
 */
public class ShareInfoBody {

    @SerializedName("ShareContent")
    public ShareInfo shareInfo;
}
