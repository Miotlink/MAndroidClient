package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.data.entity.LifeServiceEntity;

import java.util.List;

/**
 * Created by Administrator on 2015/12/23.
 *
 */

public class LifeServiceDataSegment {
    @SerializedName("Services")
   private List<LifeServiceEntity> services;

    public List<LifeServiceEntity> getServices() {
        return services;
    }
}
