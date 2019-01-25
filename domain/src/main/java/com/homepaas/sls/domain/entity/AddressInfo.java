package com.homepaas.sls.domain.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CJJ on 2016/9/13.
 *
 */

public class AddressInfo {

    @SerializedName("Address")
    public List<AddressEntity> addressCollections;

    public List<AddressEntity> getAddressCollections() {
        return addressCollections;
    }

    public void setAddressCollections(List<AddressEntity> addressCollections) {
        this.addressCollections = addressCollections;
    }
}
