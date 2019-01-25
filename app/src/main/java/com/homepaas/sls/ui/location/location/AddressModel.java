package com.homepaas.sls.ui.location.location;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by xiejiali on 2016/6/14.
 */
public class AddressModel implements Parcelable {
    public LatLng latLng;
    //private ReverseGeoCodeResult result;
    public String poiName;
    public String poiAddress;
    public String resultAdress;
    public String province;
    public String city;
    public String district;
    public String street;
    public String streetNum;

    public AddressModel(LatLng latLng, String poiName, String poiAddress, String resultAdress, String province, String city, String district, String street, String streetNum) {
        this.latLng = latLng;
        this.poiName = poiName;
        this.poiAddress = poiAddress;
        this.resultAdress = resultAdress;
        this.province = province;
        this.city = city;
        this.district = district;
        this.street = street;
        this.streetNum = streetNum;
    }

    @Override
    public String toString() {
        return "AddressModel{" +
                "latLng=" + latLng +
                ", poiName='" + poiName + '\'' +
                ", poiAddress='" + poiAddress + '\'' +
                ", resultAdress='" + resultAdress + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", streetNum='" + streetNum + '\'' +
                '}';
    }
//    private GeoCoderHelper geoCoderHelper;
//    private static AddressModel addressModel = null;
//
//    public static AddressModel getAddressModel(LatLng latLng, ReverseGeoCodeResult result) {
//
//        addressModel = new AddressModel(latLng,result);
//        return addressModel;
//    }
//
//    public void reverseGeoCod() {
//        geoCoderHelper = new GeoCoderHelper();
//        geoCoderHelper.setLatLng(latLng);
//        geoCoderHelper.reverseGeoCode();
//        geoCoderHelper.setOnReverseGeoCodeResultListener(new GeoCoderHelper.onReverseGeoCodeResultListener() {
//            @Override
//            public void result(Object obj, LatLng latLng) {
//                ReverseGeoCodeResult reverseGeoCodeResult = (ReverseGeoCodeResult) obj;
//                addressModel.setAdress(((ReverseGeoCodeResult) obj).getAddress());
//                addressModel.setProvince(((ReverseGeoCodeResult) obj).getAddressDetail().province);
//                addressModel.setCity(((ReverseGeoCodeResult) obj).getAddressDetail().city);
//                addressModel.setDistrict(((ReverseGeoCodeResult) obj).getAddressDetail().district);
//                addressModel.setStreet(((ReverseGeoCodeResult) obj).getAddressDetail().street);
//                addressModel.setStreetNum(((ReverseGeoCodeResult) obj).getAddressDetail().streetNumber);
//            }
//        });
//    }


//    public ReverseGeoCodeResult getResult() {
//        return result;
//    }
//
//    public void setResult(ReverseGeoCodeResult result) {
//        this.result = result;
//    }
//
//    public void setData() {
//        setAdress(result.getAddress());
//        setProvince(result.getAddressDetail().province);
//        setCity(result.getAddressDetail().city);
//        setDistrict(result.getAddressDetail().district);
//        setStreet(result.getAddressDetail().street);
//        setStreetNum(result.getAddressDetail().streetNumber);
//    }
//
//    public AddressModel(LatLng latLng, ReverseGeoCodeResult result) {
//        this.latLng = latLng;
//        this.result = result;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.latLng, flags);
        dest.writeString(this.poiName);
        dest.writeString(this.poiAddress);
        dest.writeString(this.resultAdress);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.district);
        dest.writeString(this.street);
        dest.writeString(this.streetNum);
    }

    protected AddressModel(Parcel in) {
        this.latLng = in.readParcelable(LatLng.class.getClassLoader());
        this.poiName = in.readString();
        this.poiAddress = in.readString();
        this.resultAdress = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.district = in.readString();
        this.street = in.readString();
        this.streetNum = in.readString();
    }

    public static final Parcelable.Creator<AddressModel> CREATOR = new Parcelable.Creator<AddressModel>() {
        @Override
        public AddressModel createFromParcel(Parcel source) {
            return new AddressModel(source);
        }

        @Override
        public AddressModel[] newArray(int size) {
            return new AddressModel[size];
        }
    };
}
