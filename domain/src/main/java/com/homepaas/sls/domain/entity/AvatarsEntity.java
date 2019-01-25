package com.homepaas.sls.domain.entity;

/**
 * Created by Sherily on 2016/9/18.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sherily on 2016/9/18.
 */
public class AvatarsEntity implements Serializable{

    @SerializedName("Avatars")
    private List<String> avatars;

    public List<String> getAvatars() {
        return avatars;
    }

    public void setAvatars(List<String> avatars) {
        this.avatars = avatars;
    }
}

