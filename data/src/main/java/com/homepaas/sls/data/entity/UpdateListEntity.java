package com.homepaas.sls.data.entity;

/**
 * Created by mhy on 2017/9/1.
 */

public class UpdateListEntity {
    private boolean isUpdateList;

    public boolean isUpdateList() {
        return isUpdateList;
    }

    public UpdateListEntity(boolean isUpdateList) {
        this.isUpdateList = isUpdateList;
    }

    public void setUpdateList(boolean updateList) {
        isUpdateList = updateList;
    }
}
