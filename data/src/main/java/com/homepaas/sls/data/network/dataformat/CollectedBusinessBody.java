package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.BusinessCollectionEntity;

import java.io.Serializable;
import java.util.List;

/**
 * on 2016/2/18 0018
 *
 * @author zhudongjie .
 */
public class CollectedBusinessBody implements Serializable{

    @SerializedName("BusinessFavoritesList")
    private List<BusinessCollectionEntity> collectedBusinesses;

    public List<BusinessCollectionEntity> getCollectedBusinesses() {
        return collectedBusinesses;
    }

    public CollectedBusinessBody(List<BusinessCollectionEntity> collectedBusinesses) {
        this.collectedBusinesses = collectedBusinesses;
    }
}
