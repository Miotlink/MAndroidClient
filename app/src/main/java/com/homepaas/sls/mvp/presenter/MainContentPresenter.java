package com.homepaas.sls.mvp.presenter;

import android.support.annotation.StringDef;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.Log;

import com.homepaas.sls.BuildConfig;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.BannerInfo;
import com.homepaas.sls.domain.entity.BusinessEntity;
import com.homepaas.sls.domain.entity.CheckIsReceivedRedCoupsEntry;
import com.homepaas.sls.domain.entity.IService;
import com.homepaas.sls.domain.entity.ServiceSearchInfo;
import com.homepaas.sls.domain.entity.UpdateCheck;
import com.homepaas.sls.domain.entity.WorkerEntity;
import com.homepaas.sls.domain.interactor.AddRecommendInfoInteractor;
import com.homepaas.sls.domain.interactor.BannersInfoInteractor;
import com.homepaas.sls.domain.interactor.CheckBusinessCallableInteractor;
import com.homepaas.sls.domain.interactor.CheckUpdateInteractor;
import com.homepaas.sls.domain.interactor.CheckWorkerCallableInteractor;
import com.homepaas.sls.domain.interactor.SearchServiceListInteractor;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.domain.param.SearchParameter;
import com.homepaas.sls.domain.repository.CheckIsReceivedRedCoupsRepo;
import com.homepaas.sls.event.EventPhoneInfo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.mvp.model.WorkerBussinesModel;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;
import com.homepaas.sls.mvp.model.mapper.ServiceInfoModelMapper;
import com.homepaas.sls.mvp.view.MainContentView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_DEFAULT;
import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_TEXT;
import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_TYPE;
import static com.homepaas.sls.domain.param.Constant.QUERY_MODE_VOICE;
import static com.homepaas.sls.domain.param.Constant.SERVICE_TYPE_ALL;
import static com.homepaas.sls.domain.param.Constant.SERVICE_TYPE_BUSINESS;
import static com.homepaas.sls.domain.param.Constant.SERVICE_TYPE_WORKER;

/**
 * on 2016/1/14 0014
 *
 * @author zhudongjie .
 */
@ActivityScope
public class MainContentPresenter implements Presenter {

    private static final String TAG = "MainContentPresenter";

    private static final boolean DEBUG = BuildConfig.DEBUG;

    @StringDef({SERVICE_TYPE_ALL, SERVICE_TYPE_WORKER, SERVICE_TYPE_BUSINESS})
    public @interface ServiceType {
    }

    @StringDef({QUERY_MODE_TEXT, QUERY_MODE_VOICE, QUERY_MODE_TYPE, QUERY_MODE_DEFAULT})
    public @interface QueryMode {
    }

    private CheckWorkerCallableInteractor callableWorkerInteractor;

    private CheckBusinessCallableInteractor callableBusinessInteractor;
    private MainContentView mainContentView;

    private SearchServiceListInteractor serviceListInteractor;

    private ServiceInfoModelMapper serviceInfoModelMapper;

    private SimpleArrayMap<String, BusinessInfoModel> businessMap = new SimpleArrayMap<>();

    private SimpleArrayMap<String, WorkerCollectionEntity> workerMap = new SimpleArrayMap<>();

    private IServiceInfo selectedInfo;
    private WorkerBussinesModel selectedWorkerBussinesModel;

    private boolean searching;

    private CheckUpdateInteractor checkUpdateInteractor;

    private BannersInfoInteractor bannersInfoInteractor;

    private AddRecommendInfoInteractor addRecommendInfoInteractor;

    @Inject
    CheckIsReceivedRedCoupsRepo checkIsReceivedRedCoupsRepo;
    @Inject
    public MainContentPresenter(SearchServiceListInteractor serviceListInteractor,
                                ServiceInfoModelMapper serviceInfoModelMapper, CheckUpdateInteractor checkUpdateInteractor,
                                CheckWorkerCallableInteractor callableWorkerInteractor, CheckBusinessCallableInteractor callableBusinessInteractor,
                                BannersInfoInteractor bannersInfoInteractor,AddRecommendInfoInteractor addRecommendInfoInteractor) {
        this.serviceListInteractor = serviceListInteractor;
        this.serviceInfoModelMapper = serviceInfoModelMapper;
        this.checkUpdateInteractor = checkUpdateInteractor;
        this.callableWorkerInteractor = callableWorkerInteractor;
        this.callableBusinessInteractor = callableBusinessInteractor;
        this.bannersInfoInteractor = bannersInfoInteractor;
        this.addRecommendInfoInteractor = addRecommendInfoInteractor;
    }

    public void setMainView(MainContentView mainContentView) {
        this.mainContentView = mainContentView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        serviceListInteractor.unsubscribe();
        checkUpdateInteractor.unsubscribe();
        callableBusinessInteractor.unsubscribe();
        callableWorkerInteractor.unsubscribe();
        bannersInfoInteractor.unsubscribe();
        addRecommendInfoInteractor.unsubscribe();
    }
    public void addRecommendInfo(String code){
        addRecommendInfoInteractor.setCode(code);
        addRecommendInfoInteractor.execute(new OldBaseObserver<String>(mainContentView) {
            @Override
            public void onNext(String s) {
                mainContentView.commit(s);
            }
        });
    }
    private IService curService;
    public void call(IService service){
        curService =service;
        if (curService instanceof WorkerEntity){
            WorkerEntity workerEntity = (WorkerEntity)curService;
            callableWorkerInteractor.setPhoneNumber(workerEntity.getPhoneNumber());
            callableWorkerInteractor.setWorkerId(workerEntity.getId());
            callableWorkerInteractor.execute(new OldBaseObserver<Boolean>(mainContentView) {
                @Override
                public void onNext(Boolean aBoolean) {
                    mainContentView.call(curService,aBoolean);
                }
            });
        } else {
            BusinessEntity businessEntity = (BusinessEntity)curService;
            callableBusinessInteractor.setBusinessId(businessEntity.getId());
            callableBusinessInteractor.setPhoneNumber(businessEntity.getPhoneNumber());
            callableBusinessInteractor.execute(new OldBaseObserver<Boolean>(mainContentView) {
                @Override
                public void onNext(Boolean aBoolean) {
                    mainContentView.call(curService,aBoolean);
                }
            });
        }

    }
    public void call(WorkerBussinesModel workerBussinesModel){
        selectedWorkerBussinesModel =workerBussinesModel;
        Log.d("phonumber", "call: "+ workerBussinesModel.getPhoneNumber());
        if (workerBussinesModel.getType() == Constant.SERVICE_TYPE_WORKER_INT){
            callableWorkerInteractor.setPhoneNumber(workerBussinesModel.getPhoneNumber());
            callableWorkerInteractor.setWorkerId(workerBussinesModel.getId());
            callableWorkerInteractor.execute(new OldBaseObserver<Boolean>(mainContentView) {
                @Override
                public void onNext(Boolean aBoolean) {
                    mainContentView.call(selectedWorkerBussinesModel,aBoolean);
                }
            });
        } else {
            callableBusinessInteractor.setBusinessId(workerBussinesModel.getId());
            callableBusinessInteractor.setPhoneNumber(workerBussinesModel.getPhoneNumber());
            callableBusinessInteractor.execute(new OldBaseObserver<Boolean>(mainContentView) {
                @Override
                public void onNext(Boolean aBoolean) {
                    mainContentView.call(selectedWorkerBussinesModel,aBoolean);
                }
            });
        }

    }

    public IServiceInfo selectedService(String id, int type) {
        if (type == IServiceInfo.TYPE_WORKER) {
            selectedInfo = workerMap.get(id);
        } else if (type == IServiceInfo.TYPE_BUSINESS) {
            selectedInfo = businessMap.get(id);
        }
        return selectedInfo;
    }



    /**
     *
     * @param latitude
     * @param longitude
     * @param queryContent
     * @param serviceId
     * @param serviceType
     * @param queryMode 0全部，1工人，2商户
     */
    public void searchService(String latitude, String longitude, String queryContent, String serviceId, @ServiceType String serviceType, @QueryMode String queryMode) {
        if (searching) {
            return;
        }
        searching = true;
        // mainContentView.showLoading();
        if (TextUtils.isEmpty(queryContent)) {
            queryMode = Constant.QUERY_MODE_DEFAULT;
        }
        serviceListInteractor.setSearchParameter(new SearchParameter(latitude, longitude, serviceType, queryContent, queryMode, serviceId));
        serviceListInteractor.execute(new OldBaseObserver<ServiceSearchInfo>(mainContentView) {

            @Override
            public void onCompleted() {
                super.onCompleted();
                searching = false;
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                searching = false;
            }

            @Override
            public void onNext(ServiceSearchInfo serviceInfo) {
                if (DEBUG)
                    Log.d(TAG, "onNext: serviceInfo ");
               /* List<IServiceInfo> serviceInfoList = serviceInfoModelMapper.transformServiceInfoList(serviceInfo);
                setService(serviceInfoList);*/
            }
        });
    }


    public void setService(List<IServiceInfo> serviceInfoList) {
        if (serviceInfoList != null) {
            businessMap.clear();
            workerMap.clear();
            //工人id会与商户id 重复，故分开保存
            for (IServiceInfo info : serviceInfoList) {
                if (info instanceof BusinessInfoModel) {
                    businessMap.put(info.getId(), (BusinessInfoModel) info);
                } else if (info instanceof WorkerCollectionEntity) {
                    workerMap.put(info.getId(), (WorkerCollectionEntity) info);
                }
            }
            mainContentView.showService(serviceInfoList);
        }
    }

    public void refreshData(WorkerCollectionEntity iServiceInfo) {
        workerMap.put(iServiceInfo.getId(), iServiceInfo);
    }

    public void refreshData(BusinessInfoModel iServiceInfo) {
        businessMap.put(iServiceInfo.getId(), iServiceInfo);
    }

    public void checkUpdate(){

        checkUpdateInteractor.execute(new OldBaseObserver<UpdateCheck>(mainContentView) {

            @Override
            public void onNext(UpdateCheck updateCheck) {
                mainContentView.update(updateCheck);
            }

            @Override
            public void showError(Throwable t) {
//                super.showError(t);
            }
        });
    }

    /**
     * 新人红包信息
     */
    public void getCheckIsReceivedRedCoups(){
        checkIsReceivedRedCoupsRepo.getCheckIsReceivedRedCoups()
                .compose(RxUtil.<CheckIsReceivedRedCoupsEntry>applySchedulers())
                .subscribe(new OldBaseObserver<CheckIsReceivedRedCoupsEntry>(mainContentView) {
                    @Override
                    public void onNext(CheckIsReceivedRedCoupsEntry checkIsReceivedRedCoupsEntry) {
                        mainContentView.renderReceivedRedCoups(checkIsReceivedRedCoupsEntry);
                    }
                });
    }

    /**
     * 通知将要拨打电话
     */
    public void startDial() {
        EventBus.getDefault().post(new EventPhoneInfo(curService));
    }
//    /**
//     * 通知将要拨打电话
//     */
//    public void startDial() {
//        EventBus.getDefault().post(new EventPhoneInfo(selectedWorkerBussinesModel));
//    }
}
