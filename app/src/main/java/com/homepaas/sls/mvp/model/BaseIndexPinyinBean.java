package com.homepaas.sls.mvp.model;


import com.homepaas.sls.domain.entity.BaseIndexTagBean;

/**
 * Created by JWC on 2017/5/6.
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
