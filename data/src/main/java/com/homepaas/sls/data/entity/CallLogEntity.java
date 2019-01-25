package com.homepaas.sls.data.entity;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 通话记录
 *
 * on 2016/1/27 0027
 * @author zhudongjie .
 */
@DatabaseTable(tableName = "tb_callLog")
public class CallLogEntity {

    //key
    @DatabaseField(generatedId = true)
    private int id;

    // 工人或商户的Id
    @DatabaseField
    private String accountId;

    @DatabaseField
    private String name;

    @DatabaseField
    private String photoUrl;

    //1工人2商户
    @DatabaseField
    private int type;

    /**
     * 0 yes ,1 no
     */
    @DatabaseField
    private int isDialled;

    @DatabaseField
    private String phoneNumber;

    /**
     * 地址
     */
    @DatabaseField
    private String attribution;

    @DatabaseField
    private String time;

    @DatabaseField
    private long duration;

    public int getId() {
        return id;
    }

//    public void setId(String id) {
//        this.id = id;
//    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsDialled() {
        return isDialled;
    }

    public void setIsDialled(int isDialled) {
        this.isDialled = isDialled;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(String attribution) {
        this.attribution = attribution;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
