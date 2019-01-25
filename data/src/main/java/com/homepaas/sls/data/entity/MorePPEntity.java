package com.homepaas.sls.data.entity;

/**
 * Created by mhy on 2017/8/26.
 */

public class MorePPEntity {
    public static final String DELETE_ORDER="DELETE_ORDER";//删除订单
    public static final String CANCEL_ORDER="CANCEL_ORDER";//取消订单
    public static final String COMPL_AINT="COMPL_AINT";//投诉

    private String name;
    private String flag;


    public MorePPEntity(String name, String flag) {
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
}
