package com.homepaas.sls.domain.entity;

/**
 * Created by mhy on 2017/9/14.
 */

public class MerchantDetialFilterEntity {
   private String name;
   private boolean isSelect;

    public MerchantDetialFilterEntity(String name, boolean isSelect) {
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
