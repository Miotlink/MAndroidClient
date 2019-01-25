package com.homepaas.sls.mvp.presenter.newdetail;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.domain.entity.WorkerInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.interactor.AddShareRecordInteractor;
import com.homepaas.sls.domain.interactor.AvatarsInteractor;
import com.homepaas.sls.domain.interactor.CheckBusinessCallableInteractor;
import com.homepaas.sls.domain.interactor.CheckWorkerCallableInteractor;
import com.homepaas.sls.domain.interactor.CollectBusinessInteractor;
import com.homepaas.sls.domain.interactor.CollectWorkerInteractor;
import com.homepaas.sls.domain.interactor.GetBusinessEvaluationsInteractor;
import com.homepaas.sls.domain.interactor.GetBusinessInfoInteractor;
import com.homepaas.sls.domain.interactor.GetBusinessServiceContentsInteractor;
import com.homepaas.sls.domain.interactor.GetBusinessTagsInfoInteractor;
import com.homepaas.sls.domain.interactor.GetShareInfoInteractor;
import com.homepaas.sls.domain.interactor.GetWorkerEvaluationsInteractor;
import com.homepaas.sls.domain.interactor.GetWorkerInfoInteractor;
import com.homepaas.sls.domain.interactor.GetWorkerTagsInfoInteractor;
import com.homepaas.sls.domain.interactor.LikeBusinessInteractor;
import com.homepaas.sls.domain.interactor.LikeWorkerInteractor;
import com.homepaas.sls.domain.interactor.ReportBusinessInteractor;
import com.homepaas.sls.domain.interactor.ReportWorkerInteractor;
import com.homepaas.sls.domain.interactor.SaveCallLogInteractor;
import com.homepaas.sls.domain.param.Constant;
import com.homepaas.sls.event.EventPhoneInfo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.location.LocationHelper;
import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.mvp.model.PopuModle;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;
import com.homepaas.sls.mvp.model.mapper.ServiceInfoModelMapper;
import com.homepaas.sls.mvp.model.mapper.TagsInfoMapper;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.newdetail.MerchantWorkerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Sherily on 2017/2/8.
 */
@ActivityScope
public class MerchantWorkerDetailPresenter implements Presenter {

    private static final String TAG = "MerchantWorkerDetailPresenter";
    private GetBusinessInfoInteractor getBusinessInfoInteractor;

    private CollectBusinessInteractor collectBusinessInteractor;

    private LikeBusinessInteractor likeBusinessInteractor;

    private CheckBusinessCallableInteractor callbusinessableInteractor;

//
//    private BusinessPhoneView businessPhoneView;


    private BusinessInfoModel businessInfoModell;

    private GetBusinessTagsInfoInteractor getBusinessTagsInfoInteractor;

    private GetWorkerInfoInteractor getWorkerInfoInteractor;

    private GetWorkerTagsInfoInteractor getWorkerTagsInfoInteractor;

    private ServiceInfoModelMapper serviceInfoModelMapper;
    private TagsInfoMapper tagsInfoMapper;

    private LikeWorkerInteractor likeWorkerInteractor;

    private CollectWorkerInteractor collectWorkerInteractor;

    private CheckWorkerCallableInteractor workerCallableInteractor;

    private SaveCallLogInteractor saveCallLogInteractor;


//    private WorkerPhoneView workerPhoneView;

    private WorkerCollectionEntity workerInfoModel;

    private AvatarsInteractor avatarsInteractor;

    private MerchantWorkerView merchantWorkerView;


    @Inject
    GetBusinessEvaluationsInteractor evaluationsInteractor;

    @Inject
    GetBusinessServiceContentsInteractor serviceContentsInteractor;

    @Inject
    ReportBusinessInteractor reportBusinessInteractor;


    @Inject
    LocationHelper locationHelper;

    @Inject
    GetWorkerEvaluationsInteractor getWorkerEvaluationsInteractor;

    @Inject
    ReportWorkerInteractor reportWorkerInteractor;

    @Inject
    GetShareInfoInteractor getShareInfoInteractor;

    @Inject
    AddShareRecordInteractor addShareRecordInteractor;




    @Inject
    public MerchantWorkerDetailPresenter(GetBusinessInfoInteractor getBusinessInfoInteractor, CollectBusinessInteractor collectBusinessInteractor,
                                         LikeBusinessInteractor likeBusinessInteractor, CheckBusinessCallableInteractor callbusinessableInteractor,
                                         GetBusinessTagsInfoInteractor getBusinessTagsInfoInteractor, GetWorkerInfoInteractor getWorkerInfoInteractor,
                                         GetWorkerTagsInfoInteractor getWorkerTagsInfoInteractor, ServiceInfoModelMapper serviceInfoModelMapper,
                                         LikeWorkerInteractor likeWorkerInteractor, CollectWorkerInteractor collectWorkerInteractor,
                                         CheckWorkerCallableInteractor workerCallableInteractor, SaveCallLogInteractor saveCallLogInteractor,
                                         AvatarsInteractor avatarsInteractor, TagsInfoMapper tagsInfoMapper) {
        this.getBusinessInfoInteractor = getBusinessInfoInteractor;
        this.collectBusinessInteractor = collectBusinessInteractor;
        this.likeBusinessInteractor = likeBusinessInteractor;
        this.callbusinessableInteractor = callbusinessableInteractor;
        this.getBusinessTagsInfoInteractor = getBusinessTagsInfoInteractor;
        this.getWorkerInfoInteractor = getWorkerInfoInteractor;
        this.getWorkerTagsInfoInteractor = getWorkerTagsInfoInteractor;
        this.serviceInfoModelMapper = serviceInfoModelMapper;
        this.likeWorkerInteractor = likeWorkerInteractor;
        this.collectWorkerInteractor = collectWorkerInteractor;
        this.workerCallableInteractor = workerCallableInteractor;
        this.saveCallLogInteractor = saveCallLogInteractor;
        this.avatarsInteractor = avatarsInteractor;
        this.tagsInfoMapper = tagsInfoMapper;
    }

    public void setMerchantWorkerView(MerchantWorkerView merchantWorkerView) {
        this.merchantWorkerView = merchantWorkerView;
    }
    public void getBusinessAvatars(String id){
        avatarsInteractor.setType("2");
        avatarsInteractor.setId(id);
        avatarsInteractor.execute(new OldBaseObserver<AvatarsEntity>(merchantWorkerView) {
            @Override
            public void onNext(AvatarsEntity avatarsEntity) {
                merchantWorkerView.renderAvatars(avatarsEntity);
            }
        });
    }
    public void getWorkerAvatars(String id){
        avatarsInteractor.setType("1");
        avatarsInteractor.setId(id);
        avatarsInteractor.execute(new OldBaseObserver<AvatarsEntity>(merchantWorkerView) {
            @Override
            public void onNext(AvatarsEntity avatarsEntity) {
                merchantWorkerView.renderAvatars(avatarsEntity);
            }
        });
    }
    public void getBusinessTagsInfo(final String businessId){
        getBusinessTagsInfoInteractor.setBusinessId(businessId);
        getBusinessTagsInfoInteractor.execute(new OldBaseObserver<GetBusinessTagsInfo>(merchantWorkerView) {
            @Override
            public void onNext(GetBusinessTagsInfo getBusinessTagsInfo) {
                merchantWorkerView.renderTags(tagsInfoMapper.transform(getBusinessTagsInfo));
            }
        });
    }
    public void getWorkerTagsInfo(String workerId){
//        workerView.showLoading();
        getWorkerTagsInfoInteractor.setWorkerId(workerId);
        getWorkerTagsInfoInteractor.execute(new OldBaseObserver<GetWorkerTagsInfo>(merchantWorkerView) {
            @Override
            public void onNext(GetWorkerTagsInfo getWorkerTagsInfo) {
                merchantWorkerView.renderTags(tagsInfoMapper.transform(getWorkerTagsInfo));
            }
        });

    }
    public void getBusinessInfo(String businessId) {
        merchantWorkerView.showLoading();
        getBusinessInfoInteractor.setBusinessId(businessId);
        getBusinessInfoInteractor.execute(new OldBaseObserver<BusinessInfo>(merchantWorkerView) {
            @Override
            public void onNext(BusinessInfo businessInfo) {
                businessInfoModell = serviceInfoModelMapper.transformBusiness(businessInfo);
//                if (TextUtils.isEmpty(businessInfoModell.getDistance()) || "0km".equals(businessInfoModell.getDistance())) {
//                    businessInfoModell.setDistance(getDistance(new LatLng(businessInfoModell.getLatitude(), businessInfoModell.getLongitude())));
//                }
                if (merchantWorkerView != null){
                    merchantWorkerView.renderBusinessInfo(businessInfoModell);
                    merchantWorkerView.renderTags(tagsInfoMapper.transform(businessInfoModell.getGetBusinessTagsInfo()));
                }

//                if (businessPhoneView != null)
//                    businessPhoneView.renderBusinessPhoneView(businessInfo.getPhoneNumber(),TextUtils.equals(businessInfo.getIsCall(),"1"));
            }

        });
    }
    public void getWorkerInfo(final String workerId) {
        merchantWorkerView.showLoading();
        getWorkerInfoInteractor.setWorkerId(workerId);
        getWorkerInfoInteractor.execute(new OldBaseObserver<WorkerInfo>(merchantWorkerView) {
            @Override
            public void onNext(WorkerInfo workerInfo) {
                workerInfoModel = serviceInfoModelMapper.transformWorker(workerInfo);
//                if (TextUtils.isEmpty(workerInfoModel.getDistance()) || "0km".equals(workerInfoModel.getDistance())) {
//                    workerInfoModel.setDistance(getDistance(new LatLng(workerInfoModel.getLatitude(), workerInfoModel.getLongitude())));
//                }
                merchantWorkerView.renderWorkerInfo(workerInfoModel);
                merchantWorkerView.renderTags(tagsInfoMapper.transform(workerInfoModel.getGetWorkerTagsInfo()));
            }
        });
    }
    public void likeBusiness(String id, boolean like) {
        likestatus = like;
        likeBusinessInteractor.setBusinessId(id);
        likeBusinessInteractor.setLike(like);
        likeBusinessInteractor.execute(new OldBaseObserver<Boolean>(merchantWorkerView) {
            @Override
            public void showError(Throwable e) {
                if (e instanceof AuthException) {
                    merchantWorkerView.showLogin();
                } else {
                    super.showError(e);
                }
            }

            @Override
            public void onNext(Boolean sucess) {
//                businessInfoModell = serviceInfoModelMapper.transformBusiness(businessInfo);
                merchantWorkerView.likeBusiness(sucess,likestatus);
            }
        });
    }
    public void like(String workerId, final boolean like) {
        likestatus = like;
        likeWorkerInteractor.setLike(like);
        likeWorkerInteractor.setWorkerId(workerId);
        likeWorkerInteractor.execute(new OldBaseObserver<Boolean>(merchantWorkerView) {

            @Override
            public void showError(Throwable e) {
                if (e instanceof AuthException) {
                    merchantWorkerView.showLogin();
                } else {
                    super.showError(e);
                }
            }

            @Override
            public void onNext(Boolean sucess) {
//                workerInfoModel = serviceInfoModelMapper.transformWorker(workerInfo);
                merchantWorkerView.likeWorker(sucess,likestatus);
            }
        });
    }
    public void collectBusiness(String id,  boolean collect) {
        collectstatus = collect;
        collectBusinessInteractor.setBusinessId(id);
        collectBusinessInteractor.setCollect(collect);
        collectBusinessInteractor.execute(new OldBaseObserver<Boolean>(merchantWorkerView) {
            @Override
            public void showError(Throwable e) {
                if (e instanceof AuthException) {
                    merchantWorkerView.showLogin();
                } else {
                    super.showError(e);
                }
            }

            @Override
            public void onNext(Boolean sucess) {
//                businessInfoModell = serviceInfoModelMapper.transformBusiness(businessInfo);

                merchantWorkerView.collectBusiness(sucess,collectstatus);
            }
        });
    }

    public List<PopuModle> getPopuModule(String type){
        List<PopuModle> popuModles = new ArrayList<>();
        if (TextUtils.equals(Constant.SERVICE_TYPE_BUSINESS, type)){
            popuModles.add(new PopuModle("收藏",businessInfoModell.isCollected()));
//            popuModles.add(new PopuModle("分享",false));
            popuModles.add(new PopuModle("点赞",businessInfoModell.isLike()));
        }else {
            popuModles.add(new PopuModle("收藏",workerInfoModel.isCollected()));
//            popuModles.add(new PopuModle("分享",false));
            popuModles.add(new PopuModle("点赞",workerInfoModel.isLike()));
        }
        return  popuModles;
    }
    private boolean collectstatus;
    private boolean likestatus;
    public void collect(String workerId, boolean collect) {
        collectstatus = collect;
        collectWorkerInteractor.setCollect(collect);
        collectWorkerInteractor.setWorkerId(workerId);
        collectWorkerInteractor.execute(new OldBaseObserver<Boolean>(merchantWorkerView) {

            @Override
            public void showError(Throwable e) {
                if (e instanceof AuthException) {
                    merchantWorkerView.showLogin();
                } else {
                    super.showError(e);
                }
            }

            @Override
            public void onNext(Boolean sucess) {
//                workerInfoModel = serviceInfoModelMapper.transformWorker(workerInfo);

                merchantWorkerView.collectWorker(sucess,collectstatus);
            }
        });
    }

    public void attemptBusinessCall() {
        //  businessView.showLoading();
        callbusinessableInteractor.setBusinessId(businessInfoModell.getId());
        final String phone = businessInfoModell.getPhoneNumber();
        Log.d("phonenumber", "attemptCall: " + phone);
        callbusinessableInteractor.setPhoneNumber(phone);
        callbusinessableInteractor.execute(new OldBaseObserver<Boolean>(merchantWorkerView) {
            @Override
            public void onError(Throwable t) {
                super.onError(t);
                merchantWorkerView.callIfEnabled(false, phone);
            }

            @Override
            public void onNext(Boolean callable) {
                merchantWorkerView.callIfEnabled(callable, phone);
            }
        });
    }
    public void attemptCall() {
        // workerView.showLoading();
        if(workerInfoModel!=null) {
            workerCallableInteractor.setWorkerId(workerInfoModel.getId());
            final String phone = workerInfoModel.getPhoneNumber();
            Log.d("phonenumber", "attemptCall: " + phone);
            workerCallableInteractor.setPhoneNumber(phone);
            workerCallableInteractor.execute(new OldBaseObserver<Boolean>(merchantWorkerView) {
                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    merchantWorkerView.callIfEnabled(false, phone);
                }

                @Override
                public void onNext(Boolean callable) {
                    merchantWorkerView.callIfEnabled(callable, phone);
                }
            });
        }
    }
    public BusinessInfoModel getBusinessInfoModel(){
        return businessInfoModell;
    }
    public String getBusinessPhoneNumber() {
        return businessInfoModell.getPhoneNumber();
    }


    public void startBusinessDial() {
        EventBus.getDefault().post(new EventPhoneInfo(businessInfoModell));
    }
    public WorkerCollectionEntity getWorkerInfoModel(){
        return workerInfoModel;
    }

    public String getPhoneNumber() {
        return workerInfoModel.getPhoneNumber();
    }
    public void startWorkerDial() {
        EventBus.getDefault().post(new EventPhoneInfo(workerInfoModel));
    }

    public void share() {
        getShareInfoInteractor.setId(Constant.SHARE_TYPE_WORKER_BUSINESS_MAIN_PAGE);
        getShareInfoInteractor.execute(new OldBaseObserver<ShareInfo>(merchantWorkerView) {
            @Override
            public void onNext(ShareInfo shareInfo) {
                merchantWorkerView.share(shareInfo);
            }
        });
    }


    public void uploadShareInfo(){
        addShareRecordInteractor.setId(Constant.SHARE_TYPE_WORKER_BUSINESS_MAIN_PAGE);
        addShareRecordInteractor.execute(new OldBaseObserver<String>(merchantWorkerView) {
            @SuppressLint("LongLogTag")
            @Override
            public void onNext(String s) {
                Log.i(TAG, "uploadShareInfo: "+s);
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
        getBusinessInfoInteractor.unsubscribe();
        collectBusinessInteractor.unsubscribe();
        likeBusinessInteractor.unsubscribe();
        callbusinessableInteractor.unsubscribe();
        getBusinessTagsInfoInteractor.unsubscribe();
        getWorkerInfoInteractor.unsubscribe();
        getWorkerTagsInfoInteractor.unsubscribe();
        likeWorkerInteractor.unsubscribe();
        collectWorkerInteractor.unsubscribe();
        workerCallableInteractor.unsubscribe();
        saveCallLogInteractor.unsubscribe();
        avatarsInteractor.unsubscribe();
        evaluationsInteractor.unsubscribe();
        serviceContentsInteractor.unsubscribe();
        reportBusinessInteractor.unsubscribe();
        getWorkerEvaluationsInteractor.unsubscribe();
        reportWorkerInteractor.unsubscribe();
        getShareInfoInteractor.unsubscribe();
        addShareRecordInteractor.unsubscribe();
    }
}
