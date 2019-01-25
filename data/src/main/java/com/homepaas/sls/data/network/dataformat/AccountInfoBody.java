package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.AccountInfo;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
public class AccountInfoBody {

    @SerializedName("AccountInfo")
    public AccountInfo accountInfo;

}
