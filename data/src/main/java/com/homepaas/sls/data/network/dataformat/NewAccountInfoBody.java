package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.NewAccountInfo;

/**
 * Created by Administrator on 2016/12/6.
 */

public class NewAccountInfoBody {
    @SerializedName("NewAccountInfo")
    public NewAccountInfo newAccountInfo;
}
