package com.homepaas.sls.mvp.model;

/**
 * on 2016/1/28 0028
 *
 * @author zhudongjie .
 */
public class CallLogModel {

    private String id;

    private String name;

    private String photoUrl;

    private int type;

    private boolean dialled;

    private String phoneNumber;

    private String attribution;

    private String time;

    private int count;

    private boolean callable = true;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isCallable() {
        return callable;
    }

    public void setCallable(boolean callable) {
        this.callable = callable;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CallLogModel{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", photoUrl='").append(photoUrl).append('\'');
        sb.append(", type=").append(type);
        sb.append(", dialled=").append(dialled);
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", attribution='").append(attribution).append('\'');
        sb.append(", time='").append(time).append('\'');
        sb.append(", count=").append(count);
        sb.append(", callable=").append(callable);
        sb.append('}');
        return sb.toString();
    }
}
