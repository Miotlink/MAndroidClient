package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2016/9/13.
 * Author: fmisser
 * Describe: 服务类型数据结构
 */

public class ServiceTypeEx implements Serializable{

    @SerializedName("TypeId")
    private String typeId;

    @SerializedName("TypeName")
    private String typeName;

    @SerializedName("Icon1")
    private String icon1;

    @SerializedName("Icon2")
    private String icon2;

    @SerializedName("Children")
    private List<ServiceTypeEx> children;

    public String getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public List<ServiceTypeEx> getChildren() {
        return children;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setChildren(List<ServiceTypeEx> children) {
        this.children = children;
    }

    public String getIcon1() {
        return icon1;
    }

    public void setIcon1(String icon1) {
        this.icon1 = icon1;
    }

    public String getIcon2() {
        return icon2;
    }

    public void setIcon2(String icon2) {
        this.icon2 = icon2;
    }
}
