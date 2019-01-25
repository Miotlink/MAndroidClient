package com.homepaas.sls.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by Administrator on 2016/1/15.
 */
public class ServiceItemModel implements Parcelable {

    private String id;

    private String name;


    public ServiceItemModel() {
    }

    protected ServiceItemModel(Parcel in) {
        id = in.readString();
        name = in.readString();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static final Creator<ServiceItemModel> CREATOR = new Creator<ServiceItemModel>() {
        @Override
        public ServiceItemModel createFromParcel(Parcel in) {
            return new ServiceItemModel(in);
        }

        @Override
        public ServiceItemModel[] newArray(int size) {
            return new ServiceItemModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }
}
