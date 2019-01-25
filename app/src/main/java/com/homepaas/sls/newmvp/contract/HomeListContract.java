package com.homepaas.sls.newmvp.contract;

import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.mvp.model.HomeListData;
import com.homepaas.sls.mvp.view.HomePageView;
import com.homepaas.sls.newmvp.base.BaseModel;
import com.homepaas.sls.newmvp.base.BasePresenter;
import com.homepaas.sls.newmvp.base.BaseView;

/**
 *  on 2017/6/4.
 */

public interface HomeListContract {

    interface Model extends BaseModel {
        void modelGetData(HomePageView homePageView, String curLongtitude, String curLatitude, RetrofitRequestCallBack<HomeListData> retrofitRequestCallBack);
    }

    interface View extends BaseView {
        void initDataSuccess(String json);
    }

    interface Presenter extends BasePresenter<View> {
        void getHomeListData();
    }
}
