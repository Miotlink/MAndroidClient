package com.homepaas.sls.mvp.view;

import com.homepaas.sls.domain.entity.IService;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * Created by CJJ on 2016/9/8.
 *
 */
public interface ServiceSearchView extends BaseView {

    void render(List<IService> searchInfoList,boolean hasWholeMerchant,int wholeAllNumber);
    void onPreOrder(String serviceTypeId,String serviceName);
    void showError(Throwable e);
    void homeShowLoading();
    void homeHighLoging();
    void mapDataGetError();
}
