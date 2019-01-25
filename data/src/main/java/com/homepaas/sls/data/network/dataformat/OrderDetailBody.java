package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.DetailOrder;
import com.homepaas.sls.domain.entity.DetailOrderEntity;

/**
 * Created by CMJ on 2016/6/21.
 */

public class OrderDetailBody {

    @SerializedName("Order")
    DetailOrderEntity detailOrder;

    public OrderDetailBody() {
    }

    public DetailOrderEntity getDetailOrder() {
        return detailOrder;
    }

    public void setDetailOrder(DetailOrderEntity detailOrder) {
        this.detailOrder = detailOrder;
    }
}
