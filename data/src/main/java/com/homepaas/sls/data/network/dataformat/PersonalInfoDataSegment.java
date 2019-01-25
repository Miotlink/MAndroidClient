package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.data.entity.PersonalInfoEntity;

/**
 * Created by Administrator on 2015/12/23.
 *
 */

public class PersonalInfoDataSegment {
    @SerializedName("Info")
    PersonalInfoEntity info;

    public PersonalInfoEntity getInfo() {
        return info;
    }
}
