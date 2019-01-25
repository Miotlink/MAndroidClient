package com.homepaas.sls.mvp.presenter.servicedetail;

import android.text.TextUtils;
import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;
import com.homepaas.sls.domain.entity.ServiceContent;
import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.interactor.AddShareRecordInteractor;
import com.homepaas.sls.domain.interactor.AvatarsInteractor;
import com.homepaas.sls.domain.interactor.CheckBusinessCallableInteractor;
import com.homepaas.sls.domain.interactor.CollectBusinessInteractor;
import com.homepaas.sls.domain.interactor.GetBusinessEvaluationsInteractor;
import com.homepaas.sls.domain.interactor.GetBusinessInfoInteractor;
import com.homepaas.sls.domain.interactor.GetBusinessServiceContentsInteractor;
import com.homepaas.sls.domain.interactor.GetBusinessTagsInfoInteractor;
import com.homepaas.sls.domain.interactor.GetShareInfoInteractor;
import com.homepaas.sls.domain.interactor.LikeBusinessInteractor;
import com.homepaas.sls.domain.interactor.ReportBusinessInteractor;
import com.homepaas.sls.domain.interactor.SaveCallLogInteractor;
import com.homepaas.sls.event.EventPhoneInfo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.location.LocationHelper;
import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.mvp.model.IServiceInfo.SystemCertification;
import com.homepaas.sls.mvp.model.mapper.ServiceInfoModelMapper;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.BusinessPhoneView;
import com.homepaas.sls.mvp.view.servicedetail.BusinessView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

/**
 * on 2016/1/18 0018
 *
 * @author zhudongjie .
 */
@ActivityScope
public class BusinessDetailPresenter implements Presenter {

    private static final String TAG = "BusinessDetailPresenter";
    private GetBusinessInfoInteractor getBusinessInfoInteractor;

    private CollectBusinessInteractor collectBusinessInteractor;

    private LikeBusinessInteractor likeBusinessInteractor;

    private CheckBusinessCallableInteractor callableInteractor;

    private BusinessView businessView;

    private BusinessPhoneView businessPhoneView;

    private ServiceInfoModelMapper serviceInfoModelMapper;

    private BusinessInfoModel infoModel;

    private SaveCallLogInteractor saveCallLogInteractor;
    private GetBusinessTagsInfoInteractor getBusinessTagsInfoInteractor;
    private AvatarsInteractor avatarsInteractor;

    @Inject
    LocationHelper locationHelper;

    @Inject
    GetBusinessEvaluationsInteractor evaluationsInteractor;

    @Inject
    GetBusinessServiceContentsInteractor serviceContentsInteractor;

    @Inject
    ReportBusinessInteractor reportBusinessInteractor;

    @Inject
    GetShareInfoInteractor getShareInfoInteractor;

    @Inject
    AddShareRecordInteractor addShareRecordInteractor;

    @Inject
    public BusinessDetailPresenter(GetBusinessInfoInteractor getBusinessInfoInteractor,
                                   CollectBusinessInteractor collectBusinessInteractor,
                                   LikeBusinessInteractor likeBusinessInteractor,
                                   CheckBusinessCallableInteractor callableInteractor,
                                   ServiceInfoModelMapper serviceInfoModelMapper,
                                   SaveCallLogInteractor saveCallLogInteractor,
                                   GetBusinessTagsInfoInteractor getBusinessTagsInfoInteractor,
                                   AvatarsInteractor avatarsInteractor) {
        this.getBusinessInfoInteractor = getBusinessInfoInteractor;
        this.collectBusinessInteractor = collectBusinessInteractor;
        this.likeBusinessInteractor = likeBusinessInteractor;
        this.callableInteractor = callableInteractor;
        this.serviceInfoModelMapper = serviceInfoModelMapper;
        this.saveCallLogInteractor = saveCallLogInteractor;
        this.getBusinessTagsInfoInteractor = getBusinessTagsInfoInteractor;
        this.avatarsInteractor = avatarsInteractor;
    }

    public void setBusinessView(BusinessView businessView) {
        this.businessView = businessView;
    }

    public void setBusinessPhoneView(BusinessPhoneView businessPhoneView) {
        this.businessPhoneView = businessPhoneView;
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
        saveCallLogInteractor.unsubscribe();
        evaluationsInteractor.unsubscribe();
        serviceContentsInteractor.unsubscribe();
        reportBusinessInteractor.unsubscribe();
        getShareInfoInteractor.unsubscribe();
        addShareRecordInteractor.unsubscribe();
        getBusinessTagsInfoInteractor.unsubscribe();
        avatarsInteractor.unsubscribe();
    }

    public void getBusinessAvatars(String id){
        avatarsInteractor.setType("2");
        avatarsInteractor.setId(id);
        avatarsInteractor.execute(new OldBaseObserver<AvatarsEntity>(businessView) {
            @Override
            public void onNext(AvatarsEntity avatarsEntity) {
                businessView.renderBusinessAvatars(avatarsEntity);
            }
        });
    }
    public void getBusinessTagsInfo(final String businessId){
        getBusinessTagsInfoInteractor.setBusinessId(businessId);
        getBusinessTagsInfoInteractor.execute(new OldBaseObserver<GetBusinessTagsInfo>(businessView) {

            @Override
            public void onNext(GetBusinessTagsInfo getBusinessTagsInfo) {
                businessView.render(getBusinessTagsInfo);
            }
        });
    }
    public void getBusinessInfo(String businessId) {
        businessView.showLoading();
        getBusinessInfoInteractor.setBusinessId(businessId);
        getBusinessInfoInteractor.execute(new OldBaseObserver<BusinessInfo>(businessView) {
            @Override
            public void onNext(BusinessInfo businessInfo) {
                infoModel = serviceInfoModelMapper.transformBusiness(businessInfo);
                if (TextUtils.isEmpty(infoModel.getDistance()) || "0km".equals(infoModel.getDistance())) {
                    infoModel.setDistance(getDistance(new LatLng(infoModel.getLatitude(), infoModel.getLongitude())));
                }
                if (businessView != null)
                businessView.render(infoModel);
                if (businessPhoneView != null)
                    businessPhoneView.renderBusinessPhoneView(businessInfo.getPhoneNumber(),TextUtils.equals(businessInfo.getIsCall(),"1"));
            }

        });
    }

    public void collectBusiness(String id, boolean collect) {
        collectBusinessInteractor.setBusinessId(id);
        collectBusinessInteractor.setCollect(collect);
        collectBusinessInteractor.execute(new OldBaseObserver<Boolean>(businessView) {
            @Override
            public void showError(Throwable t) {
                if (t instanceof AuthException) {
                    businessView.showLogin();
                } else {
                    super.showError(t);
                }
            }

            @Override
            public void onNext(Boolean sucess) {
//                infoModel = serviceInfoModelMapper.transformBusiness(businessInfo);
                businessView.collectBusiness(infoModel);
            }
        });
    }

    private String getDistance(LatLng desc) {
        double distance = locationHelper.getDistance(desc) / 100;
        //四舍五入一位小数
        double lDistance = (double) Math.round(distance) / 10;
        return String.format(Locale.getDefault(), "距离%skm", lDistance);
    }


    public void likeBusiness(String id, boolean like) {
        likeBusinessInteractor.setBusinessId(id);
        likeBusinessInteractor.setLike(like);
        likeBusinessInteractor.execute(new OldBaseObserver<Boolean>(businessView) {
            @Override
            public void showError(Throwable t) {
                if (t instanceof AuthException) {
                    businessView.showLogin();
                } else {
                    super.showError(t);
                }
            }

            @Override
            public void onNext(Boolean sucess) {
//                infoModel = serviceInfoModelMapper.transformBusiness(businessInfo);
                businessView.likeBusiness(infoModel);
            }
        });
    }

    public void attemptCall() {
        //  businessView.showLoading();
        callableInteractor.setBusinessId(infoModel.getId());
        final String phone = infoModel.getPhoneNumber();
        Log.d("phonenumber", "attemptCall: " + phone);
        callableInteractor.setPhoneNumber(phone);
        callableInteractor.execute(new OldBaseObserver<Boolean>(businessView) {
            @Override
            public void onError(Throwable t) {
                super.onError(t);
                businessView.callIfEnabled(false, phone);
            }

            @Override
            public void onNext(Boolean callable) {
                businessView.callIfEnabled(callable, phone);
            }
        });
    }

    private int pageIndex = 1;

    private static final int DEFAULT_SIZE = 10;

    /**
     * 获取商户评价列表
     */
    public void getBusinessEvaluations(String businessId) {
        pageIndex = 1;
        evaluationsInteractor.setBusinessId(businessId);
        evaluationsInteractor.setRange(pageIndex, DEFAULT_SIZE);
        evaluationsInteractor.execute(new OldBaseObserver<List<Evaluation>>(businessView) {
            @Override
            public void onNext(List<Evaluation> evaluations) {
                businessView.renderEvaluations(evaluations);
            }
        });
    }

    public void addMoreEvaluations() {
        evaluationsInteractor.setRange(++pageIndex, DEFAULT_SIZE);
        evaluationsInteractor.execute(new OldBaseObserver<List<Evaluation>>(businessView) {
            @Override
            public void onNext(List<Evaluation> evaluations) {
                businessView.addMoreEvaluations(evaluations);
            }
        });
    }


    /**
     * 获取服务
     */
    public void getServiceContents(String businessId) {
        serviceContentsInteractor.setBusinessId(businessId);
        serviceContentsInteractor.execute(new OldBaseObserver<List<ServiceContent>>(businessView) {

            @Override
            public void onNext(List<ServiceContent> serviceContents) {
                businessView.renderServiceContents(serviceContents);
            }
        });
    }

    /**
     * 纠错
     */
    public void report(String id) {
        reportBusinessInteractor.setBusinessId(id);
        reportBusinessInteractor.execute(new OldBaseObserver<String>(businessView) {
            @Override
            public void onNext(String s) {
                businessView.showMessage("举报成功");
            }
        });
    }

    public void share() {
        getShareInfoInteractor.execute(new OldBaseObserver<ShareInfo>(businessView) {
            @Override
            public void onNext(ShareInfo shareInfo) {
                businessView.share(shareInfo);
            }
        });
    }


    public void uploadShareInfo(){
        addShareRecordInteractor.execute(new OldBaseObserver<String>(businessView) {
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

    public BusinessInfoModel getBusinessInfoModel(){
        return infoModel;
    }

//    public List<IServiceInfo.Photo> getPhotos() {
//        return infoModel.getPhotos();
//    }

    public List<SystemCertification> getSystemCertificationList() {
        return infoModel.getSystemCertifications();
    }

    public String getPhoneNumber() {
        return infoModel.getPhoneNumber();
    }


    public void startDial() {
        EventBus.getDefault().post(new EventPhoneInfo(infoModel));
    }
}
