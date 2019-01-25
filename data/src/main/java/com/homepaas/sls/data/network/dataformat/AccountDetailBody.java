package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.AccountDetail;

import java.util.List;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
public class AccountDetailBody {

    @SerializedName("AccountDetailList")
    public List<AccountDetail> accountDetails;
}
