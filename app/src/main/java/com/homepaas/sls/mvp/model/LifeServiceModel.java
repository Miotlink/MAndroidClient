package com.homepaas.sls.mvp.model;

import java.util.List;

/**
 * Created by Administrator on 2016/1/14.
 *
 */

public class LifeServiceModel {

    private String id;
    private String logo;
    private String name;
    private List<ServiceItemModel> items;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ServiceItemModel> getItems() {
        return items;
    }

    public void setItems(List<ServiceItemModel> items) {
        this.items = items;
    }
}
