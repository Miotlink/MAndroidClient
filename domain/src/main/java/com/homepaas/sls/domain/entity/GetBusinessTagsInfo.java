package com.homepaas.sls.domain.entity;

import java.util.List;

/**
 * Created by Sheirly on 2016/9/9.
 */
public class GetBusinessTagsInfo {
    private List<BusinessTagsInfo> businessTagsInfos;
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
