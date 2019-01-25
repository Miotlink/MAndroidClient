package com.homepaas.sls.ui.widget;

import android.content.Context;
import android.content.SharedPreferences;

import com.homepaas.sls.util.StaticData;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by JWC on 2017/4/11.
 */

public class CommonAppPreferences {
    SharedPreferences sharedPreferences;

    public CommonAppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(StaticData.SPF_NAME, MODE_PRIVATE);
    }

    /**
     * 当前位置
     */
    public void setLocalAddress(String longtitude, String latitude,String city) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.LONGITUDE, longtitude);
        editor.putString(StaticData.LATITUDE, latitude);
        editor.putString(StaticData.CITY,city);
        editor.commit();
    }

    //获取经度
    public String getLongitude() {
        return sharedPreferences.getString(StaticData.LONGITUDE, "");
    }

    public String getLatitude() {
        return sharedPreferences.getString(StaticData.LATITUDE, "");
    }

    public String getCity()
    {
        return sharedPreferences.getString(StaticData.CITY,"");
    }
    //头像
    public void setHeadPortrait(String headPortraitUrl) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.HEAR_PORTIAIT, headPortraitUrl);
        editor.commit();
    }

    public String getHeadPortrait() {
        return sharedPreferences.getString(StaticData.HEAR_PORTIAIT, "");
    }

    //环信是不是第一次发送地址
    public void setImSendAddress(String imSendAddress){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.IM_SEND_ADDRESS, imSendAddress);
        editor.commit();
    }

    public String getImSendAddress(){
        return sharedPreferences.getString(StaticData.IM_SEND_ADDRESS,"0");
    }

    //添加环信的账号和密码
    public void setImInfo(String imUserName,String imPwd) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.IM_USERNAME, imUserName);
        editor.putString(StaticData.IM_PWD,imPwd);
        editor.commit();
    }

    public String getImUserName(){
        return sharedPreferences.getString(StaticData.IM_USERNAME,"");
    }

    public String getImPwd(){
        return sharedPreferences.getString(StaticData.IM_PWD,"");
    }

    //环信临时注册的账号（访客）
    public void setTemporaryImInfo(String imUserName,String imPwd) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.TEMPORARY_IM_USERNAME,imUserName);
        editor.putString(StaticData.TEMPORARY_IM_PWD,imPwd);
        editor.commit();
    }

    public String getTemporaryImUserName(){
        return sharedPreferences.getString(StaticData.TEMPORARY_IM_USERNAME,"");
    }

    public String getTemporaryImPwd(){
        return sharedPreferences.getString(StaticData.TEMPORARY_IM_PWD,"");
    }

    /**
     * 版本号s
     * @return
     */

    public String getVersionName(){
        return sharedPreferences.getString(StaticData.VERSION_NAME,"");
    }

    public void setVersionName(String versionName){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.VERSION_NAME,versionName);
        editor.commit();
    }

    /**
     * 全部分类
     * @param allServies
     */
    public void setAllServieList(String allServies,String cacheTime){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.ALL_SERVICES,allServies);
        editor.putString(StaticData.CACHE_TIME,cacheTime);
        editor.commit();
    }

    /**
     * 城市列表
     */
    public void setCityList(String citeList,String cacheTime){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.CITY_LIST,citeList);
        editor.putString(StaticData.CACHE_TIME,cacheTime);
        editor.commit();
    }

    public String getCityList(){
        return sharedPreferences.getString(StaticData.CITY_LIST,"");
    }

    public String getServieList(){
       return sharedPreferences.getString(StaticData.ALL_SERVICES,"");
    }
    public String getCacheTime(){
        return sharedPreferences.getString(StaticData.CACHE_TIME,"");
    }

    /**
     * 首页缓存的时间
     */
    public void setHomePageCacheTime(String homePageCacheTime){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.HOME_PAGE_CACHE_TIME,homePageCacheTime);
        editor.commit();
    }
    public String getHomePageCacheTime(){
        return sharedPreferences.getString(StaticData.HOME_PAGE_CACHE_TIME,"");
    }

    /**
     * 首页的banner
     */
    public void setHomeListData(String json){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(StaticData.HOME_DATA,json);
        editor.commit();
    }
    public String getHomeListData(){
        return sharedPreferences.getString(StaticData.HOME_DATA,"");
    }
}
