package com.homepaas.sls.event;

import android.support.annotation.IntDef;

/**
 * 用于通知左侧个人中心页面数据的更新
 *
 * @author zhudongjie .
 */
public class EventPersonalInfo {


    @IntDef(value = {LOGIN_STATE, PERSONAL_INFO, NEW_MESSAGE}, flag = true)
    @interface EventType {

    }

    /**
     * 登录状态变化
     */
    public static final int LOGIN_STATE = 1;

    /**
     * 个人信息变化
     */
    public static final int PERSONAL_INFO = 1 << 1;

    /**
     * 未读消息数量变化
     */
    public static final int NEW_MESSAGE = 1 << 2;

    public final int changeType;

    private boolean login;

    public EventPersonalInfo(@EventType int changeType) {
        this.changeType = changeType;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}
