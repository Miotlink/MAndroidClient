package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.BusinessTagsInfo;
import com.homepaas.sls.domain.entity.WorkerTagsInfo;

import java.util.List;

/**
 * Created by Sheirly on 2016/9/9.
 */
public class BusinessTagsBody {
    @SerializedName("TagList")
    private List<BusinessTagsInfo> businessTagsInfos;
    @SerializedName("Count")
    private int count;

    public List<BusinessTagsInfo> getBusinessTagsInfos() {
        return businessTagsInfos;
    }

    public void setBusinessTagsInfos(List<BusinessTagsInfo> businessTagsInfos) {
        this.businessTagsInfos = businessTagsInfos;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
