package com.homepaas.sls.mvp.presenter;

import android.text.TextUtils;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.AdsInfo;
import com.homepaas.sls.domain.entity.BannerInfo;
import com.homepaas.sls.domain.entity.IsFirstOrderInfo;
import com.homepaas.sls.domain.entity.UpdateCheck;
import com.homepaas.sls.domain.entity.VerifyTokenBody;
import com.homepaas.sls.domain.interactor.AdsInfoInteractor;
import com.homepaas.sls.domain.interactor.BannersInfoInteractor;
import com.homepaas.sls.domain.interactor.CheckUpdateInteractor;
import com.homepaas.sls.domain.interactor.GetFirstOrderInfoInteractor;
import com.homepaas.sls.domain.repository.VerifyTokenRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.view.MainActivityView;
import com.homepaas.sls.newmvp.contract.HomeContract;
import com.homepaas.sls.newmvp.model.utils.ModelFactory;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/12/23.
 */
@ActivityScope
public class MainActivityPresenter implements Presenter {
    private BannersInfoInteractor bannersInfoInteractor;
    private MainActivityView mainActivityView;
    private AdsInfoInteractor adsInfoInteractor;
    private GetFirstOrderInfoInteractor firstOrderInfoInteractor;
    private CheckUpdateInteractor checkUpdateInteractor;



    @Inject
    VerifyTokenRepo response;

    @Inject
    public MainActivityPresenter(BannersInfoInteractor bannersInfoInteractor, AdsInfoInteractor adsInfoInteractor,
                                 GetFirstOrderInfoInteractor firstOrderInfoInteractor, CheckUpdateInteractor checkUpdateInteractor) {
        this.bannersInfoInteractor = bannersInfoInteractor;
        this.adsInfoInteractor = adsInfoInteractor;
        this.firstOrderInfoInteractor = firstOrderInfoInteractor;
        this.checkUpdateInteractor = checkUpdateInteractor;
    }



    public void verifyToken() {
        response.verifyToken()
                .compose(RxUtil.<VerifyTokenBody>applySchedulers())
                .subscribe(new OldBaseObserver<VerifyTokenBody>(mainActivityView) {
                    @Override
                    public void onNext(VerifyTokenBody verifyTokenBody) {
                        if (verifyTokenBody != null)
                            mainActivityView.send(verifyTokenBody);
                    }

                    @Override
                    public void showError(Throwable t) {
//                        super.showError(t);
                    }
                });

    }

    public void setMainActivityView(MainActivityView mainActivityView) {
        this.mainActivityView = mainActivityView;
    }

    public void getBanner() {
        bannersInfoInteractor.execute(new OldBaseObserver<List<BannerInfo>>(mainActivityView) {
            @Override
            public void onNext(List<BannerInfo> bannersInfo) {
                mainActivityView.renderBanner(bannersInfo);
            }
        });
    }

    public void getAdsInfo() {
        adsInfoInteractor.execute(new OldBaseObserver<AdsInfo>(mainActivityView) {
            @Override
            public void onNext(AdsInfo adsInfo) {
                if (adsInfo != null) {
                    mainActivityView.version(adsInfo.getBannerInfos().size(), adsInfo.getVersion());

                }
            }
        });
    }

    public void getFistOrderInfo() {
        firstOrderInfoInteractor.execute(new OldBaseObserver<IsFirstOrderInfo>(mainActivityView) {
            @Override
            public void onNext(IsFirstOrderInfo isFirstOrderInfo) {
                mainActivityView.renderFirstOrder(TextUtils.equals("1", isFirstOrderInfo.getIsFirstOrder()), isFirstOrderInfo.getLastOrderService());
            }
        });
    }

    //检查是否有新的更新版本
    public void checkUpdate() {

        checkUpdateInteractor.execute(new OldBaseObserver<UpdateCheck>(mainActivityView) {
            @Override
            public void onNext(UpdateCheck updateCheck) {
                mainActivityView.update(updateCheck);
            }

            @Override
            public void showError(Throwable t) {
//                super.showError(t);
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
        bannersInfoInteractor.unsubscribe();
        adsInfoInteractor.unsubscribe();
        firstOrderInfoInteractor.unsubscribe();
        checkUpdateInteractor.unsubscribe();
    }
}
