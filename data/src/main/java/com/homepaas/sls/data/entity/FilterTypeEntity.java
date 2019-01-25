package com.homepaas.sls.data.entity;

/**
 * Created by mhy on 2017/8/26.
 */

public class FilterTypeEntity {
    private String name;
    private boolean isSelect;

    public FilterTypeEntity(String name, boolean isSelect) {
        this.name = name;
        this.isSelect = isSelect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
