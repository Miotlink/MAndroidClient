package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sherily on 2017/3/22.
 */

public class ShortCutEntity {
    @SerializedName("Shortcut")
    private List<ShortCutContent> shortCutContents;

    public List<ShortCutContent> getShortCutContents() {
        return shortCutContents;
    }

    public void setShortCutContents(List<ShortCutContent> shortCutContents) {
        this.shortCutContents = shortCutContents;
    }
}
