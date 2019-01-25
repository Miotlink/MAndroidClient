package com.homepaas.sls.mvp.presenter;

import android.text.TextUtils;

import com.homepaas.sls.data.entity.HomeOrderStatusEntity;
import com.homepaas.sls.data.network.RestApiHelper;
import com.homepaas.sls.data.repository.datasource.PersonalInfoPDataSource;
import com.homepaas.sls.domain.entity.IsFirstOrderInfo;
import com.homepaas.sls.domain.entity.PersonalInfo;
import com.homepaas.sls.domain.entity.SuperDiscountEntity;
import com.homepaas.sls.domain.interactor.GetFirstOrderInfoInteractor;
import com.homepaas.sls.domain.interactor.GetPersonalInfoInteractor;
import com.homepaas.sls.domain.repository.RecommendServiceRepo;
import com.homepaas.sls.domain.repository.ShortCutRepo;
import com.homepaas.sls.domain.repository.SuperDiscountRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.model.HomeListData;
import com.homepaas.sls.mvp.view.HomePageView;
import com.homepaas.sls.newmvp.contract.HomeContract;
import com.homepaas.sls.newmvp.contract.HomeListContract;
import com.homepaas.sls.newmvp.model.utils.ModelFactory;
import com.homepaas.sls.util.PreferencesUtil;
import com.homepaas.sls.util.StaticData;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/3/22.
 */

public class HomePagePresenter implements Presenter {
    @Inject
    ShortCutRepo shortCutRepo;
    @Inject
    RecommendServiceRepo recommendServiceRepo;
    @Inject
    SuperDiscountRepo superDiscountRepo;
    @Inject
    PersonalInfoPDataSource personalInfoPDataSource;
    @Inject
    public RestApiHelper restApiHelper;

    private GetFirstOrderInfoInteractor firstOrderInfoInteractor;
    private GetPersonalInfoInteractor getPersonalInfoInteractor;
    private HomeListContract.Model homeListModel = ModelFactory.getInstance().getHomeListModel();
    private HomeContract.Model iHomeModel = ModelFactory.getInstance().getHomeModel();

    private HomePageView homePageView;

    private boolean isLoadHomeData;//是否加载过首页接口，加载过，第二次不显示加载弹框

    public void setHomePageView(HomePageView homePageView) {
        this.homePageView = homePageView;
    }

    @Inject
    public HomePagePresenter(GetFirstOrderInfoInteractor firstOrderInfoInteractor, GetPersonalInfoInteractor getPersonalInfoInteractor) {
        this.firstOrderInfoInteractor = firstOrderInfoInteractor;
        this.getPersonalInfoInteractor = getPersonalInfoInteractor;
    }

    public void loadPersonalInfo() {
        getPersonalInfoInteractor.execute(new OldBaseObserver<PersonalInfo>(homePageView) {
            @Override
            public void showError(Throwable t) {
//                super.showError(t);
            }

            @Override
            public void onNext(PersonalInfo personalInfo) {
                //在登录的情况下获取个人信息，并保存telphonenumber,不需要返回给上层，已经在repo层保存
            }
        });
    }

    public void getOrderStatus() {
        iHomeModel.getOrderStatus(homePageView, new RetrofitRequestCallBack<HomeOrderStatusEntity>() {
            @Override
            public void successRequest(HomeOrderStatusEntity data) {
                homePageView.initOrderStatus(data);
            }
        });
    }

    public void getFistOrderInfo() {
        firstOrderInfoInteractor.execute(new OldBaseObserver<IsFirstOrderInfo>(homePageView) {
            @Override
            public void onNext(IsFirstOrderInfo isFirstOrderInfo) {
                homePageView.renderFirstOrder(TextUtils.equals("1", isFirstOrderInfo.getIsFirstOrder()), isFirstOrderInfo.getLastOrderService());
            }
        });
    }

    public void getSuperDiscount(String longitude, String latitude) {
        homeListModel.addSubscription(superDiscountRepo.getSuperDiscount(longitude, latitude)
                .compose(RxUtil.<SuperDiscountEntity>applySchedulers())
                .subscribe(new OldBaseObserver<SuperDiscountEntity>(homePageView) {
                    @Override
                    public void onNext(SuperDiscountEntity superDiscountEntity) {
                        PreferencesUtil.saveObject(StaticData.DISCOUNTS, superDiscountEntity);
                        homePageView.renderSuperDiscount(superDiscountEntity);
                    }

                    @Override
                    public void showError(Throwable t) {
                        super.showError(t);
                        homePageView.renderSuperDiscountError();
                    }
                }));
    }


    public void getHomeListData(String curLongtitude, String curLatitude) {
//        if (TextUtils.isEmpty(curLongtitude) || TextUtils.isEmpty(curLatitude))
//            return;
//        LogUtils.i("TAG", "首页接口开始时间" + TimeUtils.getCurrentTime());
//        if (!isLoadHomeData)
//            homePageView.showLoading(true);
        homeListModel.modelGetData(homePageView, curLongtitude, curLatitude, new RetrofitRequestCallBack<HomeListData>() {
            @Override
            public void successRequest(HomeListData homeListData) {
                isLoadHomeData = true;
                homePageView.setHomeListData(homeListData);
//                LogUtils.i("TAG", "首页接口结束时间" + TimeUtils.getCurrentTime());
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
        if (homeListModel != null)
            homeListModel.dispose();
        if (iHomeModel != null)
            iHomeModel.dispose();
        firstOrderInfoInteractor.unsubscribe();
        getPersonalInfoInteractor.unsubscribe();
    }
}
