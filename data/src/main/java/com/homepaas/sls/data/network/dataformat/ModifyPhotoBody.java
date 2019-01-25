package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/25 0025
 *
 * @author zhudongjie .
 */
public class ModifyPhotoBody {

    @SerializedName("ImgPath")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
