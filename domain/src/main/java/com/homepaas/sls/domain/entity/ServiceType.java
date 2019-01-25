package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by CJJ on 2016/9/8.
 *
 */

public class ServiceType implements Serializable{
    @SerializedName("Id")
    public String id;
    @SerializedName("Name")
    public String name;
    @SerializedName("Icon1")
    public String iconNormal;
    @SerializedName("Icon2")
    public String iconChecked;
    /**
     * 显示字段	0~6，分别表示：
     *0：无，1：工作年限，2：成立时间，3：性别年龄，4：服务范围，5：公安备案，6：籍贯
     */
    @SerializedName("DisplayAttribute")
    private String DisplayAttribute;

    public String getDisplayAttribute() {
        return DisplayAttribute;
    }

    public void setDisplayAttribute(String displayAttribute) {
        DisplayAttribute = displayAttribute;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconNormal() {
        return iconNormal;
    }

    public void setIconNormal(String iconNormal) {
        this.iconNormal = iconNormal;
    }

    public String getIconChecked() {
        return iconChecked;
    }

    public void setIconChecked(String iconChecked) {
        this.iconChecked = iconChecked;
    }
}
