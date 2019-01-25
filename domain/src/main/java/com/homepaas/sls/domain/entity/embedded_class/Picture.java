package com.homepaas.sls.domain.entity.embedded_class;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CJJ on 2016/9/11.
 */

public class Picture {
    @SerializedName("SmallPIc")
    public String smallPic;

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }
}
