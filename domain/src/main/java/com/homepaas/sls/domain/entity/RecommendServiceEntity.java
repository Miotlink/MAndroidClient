package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */

public class RecommendServiceEntity {
    @SerializedName("RecommendBlock")
    private List<RecommendBlock> recommendBlocks;

    public List<RecommendBlock> getRecommendBlocks() {
        return recommendBlocks;
    }

    public void setRecommendBlocks(List<RecommendBlock> recommendBlocks) {
        this.recommendBlocks = recommendBlocks;
    }
}
