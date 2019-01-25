package com.homepaas.sls.event;

/**
 * 拨打电话消息事件信息
 *
 * @author zhudongjie .
 */
public class EventCallPhoneInfo {


    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public EventCallPhoneInfo(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
