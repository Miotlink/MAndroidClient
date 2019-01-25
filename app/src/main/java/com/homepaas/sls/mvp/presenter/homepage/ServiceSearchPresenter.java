package com.homepaas.sls.mvp.presenter.homepage;

import android.support.annotation.StringDef;
import android.text.TextUtils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.homepaas.sls.domain.entity.IService;
import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.domain.exception.GetDataException;
import com.homepaas.sls.domain.interactor.SearchServiceInteractor;
import com.homepaas.sls.domain.param.SearchParameter;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.ServiceSearchView;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;
import com.homepaas.sls.util.gson.InterfaceDeserializer;

import java.util.Collections;
import java.util.Comparator;

import javax.inject.Inject;

import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_DEFAULT;
import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_TEXT;
import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_TYPE;
import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_VOICE;
import static com.homepaas.sls.domain.param.Constant.SERVICE_TYPE_ALL;
import static com.homepaas.sls.domain.param.Constant.SERVICE_TYPE_BUSINESS;
import static com.homepaas.sls.domain.param.Constant.SERVICE_TYPE_WORKER;

/**
 * Created by CJJ on 2016/9/8.
 */
public class ServiceSearchPresenter implements Presenter {

    private static final String TAG = "ServiceSearchPresenter";
    private LatLng locationPos;

    @StringDef({SERVICE_TYPE_ALL, SERVICE_TYPE_WORKER, SERVICE_TYPE_BUSINESS})
    public @interface ServiceType {
    }

    @StringDef({QUERY_MODE_TEXT, QUERY_MODE_VOICE, QUERY_MODE_TYPE, QUERY_MODE_DEFAULT})
    public @interface QueryMode {
    }

    private boolean isSearching = false;
    @Inject
    SearchServiceInteractor searchServiceInteractor;


    @Inject
    public ServiceSearchPresenter() {
    }

    ServiceSearchView serviceSearchView;

    public void setServiceSearchView(ServiceSearchView serviceSearchView) {
        this.serviceSearchView = serviceSearchView;
    }

    private boolean hasWholeMerchant = false;

    public void searchService(final String lat, String lng, String queryContent, final String serviceId, @ServiceType String serviceType, @QueryMode String queryMode) {
        serviceSearchView.homeHighLoging();
        serviceSearchView.homeShowLoading();
        locationPos = new LatLng(Double.valueOf(lat), Double.valueOf(lng));
        if (isSearching)
            return;
        SearchParameter searchParameter = new SearchParameter(lat, lng, serviceType, queryContent, queryMode, serviceId);
        searchServiceInteractor.setSearchParameter(searchParameter);
        searchServiceInteractor.execute(new OldBaseObserver<ServiceSearchInfo>(serviceSearchView) {
            @Override
            public void onStart() {
                super.onStart();
                isSearching = true;
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                isSearching = false;
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                isSearching = false;
                serviceSearchView.homeHighLoging();
                serviceSearchView.mapDataGetError();
            }

            @Override
            public void onNext(ServiceSearchInfo serviceSearchInfo) {
                serviceSearchView.homeHighLoging();
                hasWholeMerchant = TextUtils.equals("1", serviceSearchInfo.getHasWholeMerchant());
                isSearching = false;

                Collections.sort(serviceSearchInfo.getWrapperList(), new Comparator<IService>() {
                    @Override
                    public int compare(IService lhs, IService rhs) {
                        LatLng latLngL = new LatLng(lhs.getLat(), lhs.getLng());
                        LatLng latLngR = new LatLng(rhs.getLat(), rhs.getLng());
                        return (int) (DistanceUtil.getDistance(latLngL, locationPos) - DistanceUtil.getDistance(latLngR, locationPos));
                    }
                });
                //一键下单逻辑
                if (serviceSearchInfo.isOrdering())
                    serviceSearchView.onPreOrder(serviceSearchInfo.getServiceTypeId(), serviceSearchInfo.getServiceName());
                else {
                    //一般搜索逻辑
                    if (serviceSearchInfo == null || serviceSearchInfo.getWrapperList().isEmpty()) {
                        serviceSearchView.showError(new GetDataException("没有相应服务，请换个关键词试试"));
                        return;
                    } else {
                        serviceSearchView.render(serviceSearchInfo.getWrapperList(), hasWholeMerchant, serviceSearchInfo.getWholeServiceNumber());
                        //serviceSearchInfo 中 存在接口对象引用不能序列化 通过SP缓存json
                        Gson gson = new GsonBuilder().registerTypeAdapter(ServiceSearchInfo.class, new InterfaceDeserializer()).create();
                        String cityList=gson.toJson(serviceSearchInfo);
                        PreferencesUtil.saveString(StaticData.NEARBY, cityList);
                        PreferencesUtil.saveBoolean(StaticData.HAS_WHOLEMER_CHANT, hasWholeMerchant);
                    }
                }
            }
        });
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        searchServiceInteractor.unsubscribe();
    }
}
