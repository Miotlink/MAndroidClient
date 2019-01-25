package com.homepaas.sls.ui.location.location;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

/**
 * Created by 谢佳利 on 2016/6/12.
 * 目前只添加了反向地理解码和地理编码
 */
public class GeoCoderHelper {
    private LatLng latLng;
    private String address;
    private String city;
    private GeoCoder geoCoder;

    /**
     * 初始化
     */
    public GeoCoderHelper() {
        geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(geoCoderResultListener);
    }

    //设置实时坐标
    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    //设置实时地址
    public void setAddress(String address, String city) {
        this.address = address;
        this.city = city;
    }

    /**
     * 发起反向地理编码
     */
    public void reverseGeoCode() {
        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
    }

    /**
     * 发起地理编码
     */
    public void geoCode() {
        geoCoder.geocode(new GeoCodeOption().city(city).address(address));
    }

    public void clear() {
        if (geoCoder != null) {
            geoCoder.destroy();
        }
    }

    private OnGetGeoCoderResultListener geoCoderResultListener = new OnGetGeoCoderResultListener() {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                return;
            }
            if (onGeoCodeResultListener != null)
                onGeoCodeResultListener.result(geoCodeResult, latLng);
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

            if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                return;
            }
            onReverseGeoCodeResultListener.result(reverseGeoCodeResult, latLng);
        }
    };


    //回调接口，同步传出实时数据
    public interface onReverseGeoCodeResultListener {
        void result(Object obj, LatLng latLng);
    }

    //回调接口，同步传出实时数据
    public interface onGeoCodeResultListener {
        void result(Object obj, LatLng latLng);
    }

    private onReverseGeoCodeResultListener onReverseGeoCodeResultListener;

    public void setOnReverseGeoCodeResultListener(onReverseGeoCodeResultListener listener) {
        this.onReverseGeoCodeResultListener = listener;
    }

    private onGeoCodeResultListener onGeoCodeResultListener;

    public void setOnGeoCodeResultListener(onGeoCodeResultListener listener) {
        this.onGeoCodeResultListener = listener;
    }
}
