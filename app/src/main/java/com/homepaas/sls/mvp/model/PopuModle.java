package com.homepaas.sls.mvp.model;

/**
 * Created by Sherily on 2017/2/13.
 */

public class PopuModle {
    private String name;
    private boolean status;

    public PopuModle(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public boolean isStatus() {
        return status;
    }
}
