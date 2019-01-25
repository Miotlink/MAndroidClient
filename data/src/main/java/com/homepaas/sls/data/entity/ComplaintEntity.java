package com.homepaas.sls.data.entity;

/**
 * Created by mhy on 2017/8/26.
 * 投诉原因实体
 */

public class ComplaintEntity {
    private String name;
    private String flag;
    private boolean isCheck;

    public ComplaintEntity(String name, String flag) {
        this.name = name;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
