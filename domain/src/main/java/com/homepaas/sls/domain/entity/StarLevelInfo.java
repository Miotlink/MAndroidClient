package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2017/7/21.
 */

public class StarLevelInfo {
    @SerializedName("StarLevelLabelInfos")
    private List<StarLevelLabelInfo> starLevelLabelInfoList;

    public List<StarLevelLabelInfo> getStarLevelLabelInfoList() {
        return starLevelLabelInfoList;
    }

    public void setStarLevelLabelInfoList(List<StarLevelLabelInfo> starLevelLabelInfoList) {
        this.starLevelLabelInfoList = starLevelLabelInfoList;
    }

    public class StarLevelLabelInfo {
        //星级，如1星，2星,
        @SerializedName("Level")
        private String level;
        // 星级描述，如非常差，较差，一般，满意，非常满意,
        @SerializedName("Description")
        private String description;
        //标签
        @SerializedName("Labels")
        private List<String> Labels;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<String> getLabels() {
            return Labels;
        }

        public void setLabels(List<String> labels) {
            Labels = labels;
        }
    }
}
