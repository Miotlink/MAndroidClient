package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JWC on 2017/2/13.
 */

public class WorkerServiceTypePrice implements Serializable {
    @SerializedName("Id") //服务Id
    private String id;
    @SerializedName("Name")  //服务名字
    private String name;
    @SerializedName("Price")  //价格
    private String price;
    @SerializedName("ChildCount")  // 子集数量
    private String childCount;
    @SerializedName("Child")  //子集
    private List<WorkerServiceTypePrice> childList;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getChildCount() {
        return childCount;
    }

    public void setChildCount(String childCount) {
        this.childCount = childCount;
    }

    public List<WorkerServiceTypePrice> getChildList() {
        return childList;
    }

    public void setChildList(List<WorkerServiceTypePrice> childList) {
        this.childList = childList;
    }
}
