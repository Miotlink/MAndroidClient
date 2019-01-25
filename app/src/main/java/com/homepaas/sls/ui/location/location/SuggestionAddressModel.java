package com.homepaas.sls.ui.location.location;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by Administrator on 2016/7/28.
 */
public class SuggestionAddressModel implements Parcelable {
    private String city;
    private String district;
    private String key;
    private LatLng latLng;
    private String province;
    private String detailsAddress;

    public SuggestionAddressModel(String city, String district, String key, LatLng latLng, String province, String detailsAddress) {
        this.city = city;
        this.district = district;
        this.key = key;
        this.latLng = latLng;
        this.province = province;
        this.detailsAddress = detailsAddress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDetailsAddress() {
        return detailsAddress;
    }

    public void setDetailsAddress(String detailsAddress) {
        this.detailsAddress = detailsAddress;
    }

    public SuggestionAddressModel(String city, String district, String key, LatLng latLng) {
        this.city = city;
        this.district = district;
        this.key = key;
        this.latLng = latLng;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.city);
        dest.writeString(this.district);
        dest.writeString(this.key);
        dest.writeParcelable(this.latLng, flags);
        dest.writeString(this.province);
        dest.writeString(this.detailsAddress);
    }

    protected SuggestionAddressModel(Parcel in) {
        this.city = in.readString();
        this.district = in.readString();
        this.key = in.readString();
        this.latLng = in.readParcelable(LatLng.class.getClassLoader());
        this.province = in.readString();
        this.detailsAddress = in.readString();
    }

    public static final Parcelable.Creator<SuggestionAddressModel> CREATOR = new Parcelable.Creator<SuggestionAddressModel>() {
        @Override
        public SuggestionAddressModel createFromParcel(Parcel source) {
            return new SuggestionAddressModel(source);
        }

        @Override
        public SuggestionAddressModel[] newArray(int size) {
            return new SuggestionAddressModel[size];
        }
    };
}
