package com.homepaas.sls.newmvp.model.impl;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.homepaas.sls.SimpleTinkerInApplicationLike;
import com.homepaas.sls.data.exception.GetPersistenceDataException;
import com.homepaas.sls.data.network.dataformat.Meta;
import com.homepaas.sls.data.network.dataformat.ResponseWrapper;
import com.homepaas.sls.data.network.dataformat.ShortCutRequest;
import com.homepaas.sls.data.network.dataformat.request.TokenRequest;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.AdsInfo;
import com.homepaas.sls.domain.entity.RecommendServiceEntity;
import com.homepaas.sls.domain.entity.ShortCutEntity;
import com.homepaas.sls.httputils.BaseObserver;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.model.HomeListData;
import com.homepaas.sls.mvp.view.HomePageView;
import com.homepaas.sls.newmvp.base.IBaseModel;
import com.homepaas.sls.newmvp.contract.HomeListContract;
import com.squareup.okhttp.RequestBody;

import rx.Observable;
import rx.Observer;
import rx.functions.Func3;

import static com.homepaas.sls.data.network.RestApiHelper.EMPTY_JSON;
import static com.homepaas.sls.data.network.RestApiHelper.MEDIA_TYPE_JSON;

/**
 * Created by mhy on 2017/7/24.
 */


public class IHomeListModel extends IBaseModel implements HomeListContract.Model {

    PersonalInfoPDataSource personalInfoPDataSource = SimpleTinkerInApplicationLike.getApplicationComponent().personalInfoPDDatasource();

    @Override
    public void modelGetData(final HomePageView homePageView, String curLongtitude, String curLatitude, RetrofitRequestCallBack<HomeListData> retrofitRequestCallBack) {
        String token = null;
        RequestBody requestBody;
        try {
            token = personalInfoPDataSource.getToken();
        } catch (GetPersistenceDataException e) {
            e.printStackTrace();
            token = "";
        }
        if (TextUtils.isEmpty(token)) {
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, EMPTY_JSON);
        } else {
            TokenRequest tokenRequest = new TokenRequest(token);
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, new Gson().toJson(tokenRequest));
        }

        subscriptionArrayList.add(Observable.zip(
                apiHelper.restApi().getBannerInfo(requestBody),
                apiHelper.restApi().getShortCut(new ShortCutRequest(token, curLongtitude, curLatitude, "1")),
                apiHelper.restApi().getRecommendService(new ShortCutRequest(token, curLongtitude, curLatitude, "1")),
                new Func3<ResponseWrapper<AdsInfo>, ResponseWrapper<ShortCutEntity>, ResponseWrapper<RecommendServiceEntity>, ResponseWrapper<HomeListData>>() {

                    @Override
                    public ResponseWrapper<HomeListData> call(ResponseWrapper<AdsInfo> adsInfoResponseWrapper, ResponseWrapper<ShortCutEntity> shortCutEntityResponseWrapper, ResponseWrapper<RecommendServiceEntity> recommendServiceEntityResponseWrapper) {
                        ResponseWrapper<HomeListData> responseWrapper = new ResponseWrapper<HomeListData>();
                        HomeListData homeListData = new HomeListData();
                        responseWrapper.meta = new Meta();
                        responseWrapper.meta.setErrorCode(recommendServiceEntityResponseWrapper.meta.getErrorCode());
                        responseWrapper.meta.setErrorMsg(recommendServiceEntityResponseWrapper.meta.getErrorMsg());
                        //有一个网络错误，就视为错误
                        if (adsInfoResponseWrapper.meta.isSuccess() && shortCutEntityResponseWrapper.meta.isSuccess() && recommendServiceEntityResponseWrapper.meta.isSuccess()) {
                            homeListData.setAdsInfo(adsInfoResponseWrapper.data);
                            homeListData.setShortCutEntity(shortCutEntityResponseWrapper.data);
                            homeListData.setRecommendServiceEntity(recommendServiceEntityResponseWrapper.data);
                            responseWrapper.data = homeListData;
                        } else {
                            responseWrapper.data = homeListData;
                        }
                        return responseWrapper;
                    }
                })
                .compose(RxUtil.<ResponseWrapper<HomeListData>>applySchedulers())//处理公共线程切换代码
                .compose(RxUtil.<HomeListData>handleResponseWrapperObservable())   //处理设置httpResult返回值结果
                .compose(RxUtil.<HomeListData>progressDialogObservable(homePageView, true)) //处理弹框
                .subscribe((Observer<? super HomeListData>) new BaseObserver<HomeListData>(retrofitRequestCallBack, homePageView) {
                    @Override
                    public void showError(Throwable t) {
                        super.showError(t);
                        homePageView.getHomeListDataError();
                    }
                }));
    }
}
