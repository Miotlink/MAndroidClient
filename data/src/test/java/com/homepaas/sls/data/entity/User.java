package com.homepaas.sls.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * on 2016/2/25 0025
 *
 * @author zhudongjie .
 */
public class User {

    @SerializedName("Name")
    public String name;

    @SerializedName("Age")
    public int age;

    @SerializedName("Phones")
    public String[] phones;

}
