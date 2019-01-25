package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sherily on 2017/3/22.
 */

public class ShortCutContent implements Serializable{
    @SerializedName("IconUrl")
    private String iconUrl;
    @SerializedName("Title")
    private String title;
    @SerializedName("Tooltips")
    private String toolTips;
    @SerializedName("Service")
    private ServiceItem service;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getToolTips() {
        return toolTips;
    }

    public void setToolTips(String toolTips) {
        this.toolTips = toolTips;
    }

    public ServiceItem getService() {
        return service;
    }

    public void setService(ServiceItem service) {
        this.service = service;
    }
}
