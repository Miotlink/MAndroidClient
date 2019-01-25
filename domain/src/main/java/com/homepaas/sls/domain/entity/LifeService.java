package com.homepaas.sls.domain.entity;

import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 * 服务类型
 */

public class LifeService {

    private String id;
    private String logo;
    private String name;
    private List<LifeServiceItem> items;

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

    public List<LifeServiceItem> getItems() {
        return items;
    }

    public void setItems(List<LifeServiceItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LifeService that = (LifeService) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
