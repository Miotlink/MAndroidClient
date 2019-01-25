package com.homepaas.sls.domain.entity;

/**
 * on 2016/1/28 0028
 *
 * @author zhudongjie .
 */
public class CallLog {

    public static final int TYPE_WORKER = 1;

    public static final int TYPE_BUSINESS = 2;

    private String id;

    private String name;

    private String photoUrl;

    private int type;

    private boolean dialled;

    private String phoneNumber;

    private String attribution;

    private String time;

    private long duration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isDialled() {
        return dialled;
    }

    public void setDialled(boolean dialled) {
        this.dialled = dialled;
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
}
