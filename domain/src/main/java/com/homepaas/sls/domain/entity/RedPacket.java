package com.homepaas.sls.domain.entity;

/**
 * Created by Administrator on 2016/4/28.
 */
public class RedPacket {

    String id;
    String status;
    String createtime;
    String money;
    String validtime;

    public RedPacket() {
    }

    public RedPacket(String id, String status, String createtime, String money, String validtime) {
        this.id = id;
        this.status = status;
        this.createtime = createtime;
        this.money = money;
        this.validtime = validtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getValidtime() {
        return validtime;
    }

    public void setValidtime(String validtime) {
        this.validtime = validtime;
    }
}
