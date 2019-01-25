package com.homepaas.sls.domain.entity;

/**
 * Created by Administrator on 2017/7/21.
 */

public abstract class BaseIndexPinyinBean extends BaseIndexTagBean implements IIndexTargetInterface{
    private String pycityName;

    public String getPycityName() {
        return pycityName;
    }

    public void setPycityName(String pycityName) {
        this.pycityName = pycityName;
    }

}