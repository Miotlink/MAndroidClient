package com.homepaas.sls.domain.entity;

/**
 *
 * Created by Administrator on 2016/1/14.
 */

public class LifeServiceItem {

    public LifeServiceItem() {

    }

    public LifeServiceItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String id;

    public String name;

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

}
