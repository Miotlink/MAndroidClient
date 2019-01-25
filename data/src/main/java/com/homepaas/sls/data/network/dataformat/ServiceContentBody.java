package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.ServiceContent;

import java.util.List;

/**
 * on 2016/6/20 0020
 *
 * @author zhudongjie
 */
public class ServiceContentBody {

    @SerializedName("ServiceTypeList")
    public List<ServiceContent> serviceContents;
}
