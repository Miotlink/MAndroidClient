package com.homepaas.sls.mvp.model;

/**
 * Created by Administrator on 2016/8/31.
 */
public class WB {
    private String name;
    private String sex;
    private String age;
    private String nativePlace;
    private String time;
    private String distance;
    private String like;
    private String zan;
    private String serviceList;


    public WB(String name, String sex, String age, String nativePlace, String time, String distance, String like, String zan, String serviceList) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.nativePlace = nativePlace;
        this.time = time;
        this.distance = distance;
        this.like = like;
        this.zan = zan;
        this.serviceList = serviceList;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public String getTime() {
        return time;
    }

    public String getDistance() {
        return distance;
    }

    public String getLike() {
        return like;
    }

    public String getZan() {
        return zan;
    }

    public String getAge() {
        return age;
    }

    public String getServiceList() {
        return serviceList;
    }
}
