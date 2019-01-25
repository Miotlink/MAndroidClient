package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CJJ on 2016/9/20.
 * 获取诸如 地址标签，订单备注长度限制，图片上传限制，官方服务电话信息
 */

public class SystemConfigEntity {

    @SerializedName("addressTags")
    public List<String> addressTags;
    @SerializedName("memoLength")
    public String noteLimit;
    @SerializedName("orderPictureMax")
    public String picLimit;
    @SerializedName("_400Service")
    public String officialHotLine;

    public SystemConfigEntity() {
    }

    public List<String> getAddressTags() {
        return addressTags;
    }

    public void setAddressTags(List<String> addressTags) {
        this.addressTags = addressTags;
    }

    public String getNoteLimit() {
        return noteLimit;
    }

    public void setNoteLimit(String noteLimit) {
        this.noteLimit = noteLimit;
    }

    public String getPicLimit() {
        return picLimit;
    }

    public void setPicLimit(String picLimit) {
        this.picLimit = picLimit;
    }

    public String getOfficialHotLine() {
        return officialHotLine;
    }

    public void setOfficialHotLine(String officialHotLine) {
        this.officialHotLine = officialHotLine;
    }
}
