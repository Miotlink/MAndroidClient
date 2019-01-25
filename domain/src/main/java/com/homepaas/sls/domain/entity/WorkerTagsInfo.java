package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sherily on 2016/9/8.
 */
public class WorkerTagsInfo {
    @SerializedName("Id")
    private int Id;
    @SerializedName("TagName")
    private String tagName;
    @SerializedName("Remark")
    private String remark;

    public WorkerTagsInfo() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
