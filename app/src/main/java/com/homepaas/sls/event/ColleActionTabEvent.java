package com.homepaas.sls.event;

/**
 * Created by mhy on 2017/10/17.
 */

public class ColleActionTabEvent {

    private int tabIndex;//收藏的tab位置
    private String tabNum;// 显示的tab数量;

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public String getTabNum() {
        return tabNum;
    }

    public void setTabNum(String tabNum) {
        this.tabNum = tabNum;
    }

    public ColleActionTabEvent(int tabIndex, String tabNum) {
        this.tabIndex = tabIndex;
        this.tabNum = tabNum;
    }
}
