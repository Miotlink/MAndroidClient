package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.Recharge;

import java.util.List;

/**
 * on 2016/6/21 0021
 *
 * @author zhudongjie
 */
public class RechargeListBody {

    @SerializedName("RechargeList")
    public List<Recharge> rechargeList;
}
