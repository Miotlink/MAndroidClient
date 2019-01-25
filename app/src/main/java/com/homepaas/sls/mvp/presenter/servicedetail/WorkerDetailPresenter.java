package com.homepaas.sls.mvp.presenter.servicedetail;

import android.text.TextUtils;
import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.Evaluation;
import com.homepaas.sls.domain.entity.GetWorkerTagsInfo;
import com.homepaas.sls.domain.entity.ShareInfo;
import com.homepaas.sls.domain.entity.WorkerInfo;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.interactor.AddShareRecordInteractor;
import com.homepaas.sls.domain.interactor.AvatarsInteractor;
import com.homepaas.sls.domain.interactor.CheckWorkerCallableInteractor;
import com.homepaas.sls.domain.interactor.CollectWorkerInteractor;
import com.homepaas.sls.domain.interactor.GetShareInfoInteractor;
import com.homepaas.sls.domain.interactor.GetWorkerEvaluationsInteractor;
import com.homepaas.sls.domain.interactor.GetWorkerInfoInteractor;
import com.homepaas.sls.domain.interactor.GetWorkerTagsInfoInteractor;
import com.homepaas.sls.domain.interactor.LikeWorkerInteractor;
import com.homepaas.sls.domain.interactor.ReportWorkerInteractor;
import com.homepaas.sls.domain.interactor.SaveCallLogInteractor;
import com.homepaas.sls.event.EventPhoneInfo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.location.LocationHelper;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.mvp.model.WorkerCollectionEntity;
import com.homepaas.sls.mvp.model.mapper.ServiceInfoModelMapper;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.WorkerPhoneView;
import com.homepaas.sls.mvp.view.servicedetail.WorkerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

/**
 * on 2016/1/19 0019
 *
 * @author zhudongjie .
 */
@ActivityScope
public class WorkerDetailPresenter implements Presenter {

    private static final String TAG = "WorkerDetailPresenter";

    private GetWorkerInfoInteractor getWorkerInfoInteractor;

   private GetWorkerTagsInfoInteractor getWorkerTagsInfoInteractor;

    private ServiceInfoModelMapper serviceInfoModelMapper;

    private LikeWorkerInteractor likeWorkerInteractor;

    private CollectWorkerInteractor collectWorkerInteractor;

    private CheckWorkerCallableInteractor callableInteractor;

    private SaveCallLogInteractor saveCallLogInteractor;

    private WorkerView workerView;

    private WorkerPhoneView workerPhoneView;

    private WorkerCollectionEntity infoModel;

    private AvatarsInteractor avatarsInteractor;
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

//    @Inject
//    GetWorkerTagsInfoInteractor getWorkerTagsInfoInteractor;

    @Inject
    public WorkerDetailPresenter(CollectWorkerInteractor collectWorkerInteractor,
                                 GetWorkerInfoInteractor getWorkerInfoInteractor,
                                 ServiceInfoModelMapper serviceInfoModelMapper,
                                 LikeWorkerInteractor likeWorkerInteractor,
                                 CheckWorkerCallableInteractor callableInteractor,
                                 SaveCallLogInteractor saveCallLogInteractor,
                                 GetWorkerTagsInfoInteractor getWorkerTagsInfoInteractor,
                                 AvatarsInteractor avatarsInteractor) {
        this.collectWorkerInteractor = collectWorkerInteractor;
        this.getWorkerInfoInteractor = getWorkerInfoInteractor;
        this.serviceInfoModelMapper = serviceInfoModelMapper;
        this.likeWorkerInteractor = likeWorkerInteractor;
        this.callableInteractor = callableInteractor;
        this.saveCallLogInteractor = saveCallLogInteractor;
        this.getWorkerTagsInfoInteractor = getWorkerTagsInfoInteractor;
        this.avatarsInteractor = avatarsInteractor;
    }

    public void setWorkerView(WorkerView workerView) {
        this.workerView = workerView;
    }

    public void setWorkerPhoneView(WorkerPhoneView workerPhoneView) {
        this.workerPhoneView = workerPhoneView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getWorkerInfoInteractor.unsubscribe();
        likeWorkerInteractor.unsubscribe();
        collectWorkerInteractor.unsubscribe();
        callableInteractor.unsubscribe();
        saveCallLogInteractor.unsubscribe();
        getWorkerEvaluationsInteractor.unsubscribe();
        reportWorkerInteractor.unsubscribe();
        getShareInfoInteractor.unsubscribe();
        getWorkerTagsInfoInteractor.unsubscribe();
        avatarsInteractor.unsubscribe();
    }

    public void getWorkerAvatars(String id){
        avatarsInteractor.setType("1");
        avatarsInteractor.setId(id);
        avatarsInteractor.execute(new OldBaseObserver<AvatarsEntity>(workerView) {
            @Override
            public void onNext(AvatarsEntity avatarsEntity) {
                workerView.renderAvatars(avatarsEntity);
            }
        });
    }

    public void getWorkerTagsInfo(String workerId){
//        workerView.showLoading();
        getWorkerTagsInfoInteractor.setWorkerId(workerId);
        getWorkerTagsInfoInteractor.execute(new OldBaseObserver<GetWorkerTagsInfo>(workerView) {
            @Override
            public void onNext(GetWorkerTagsInfo getWorkerTagsInfo) {
                workerView.render(getWorkerTagsInfo);
            }
        });

    }

    public void getWorkerInfo(final String workerId) {
        workerView.showLoading();
        getWorkerInfoInteractor.setWorkerId(workerId);
        getWorkerInfoInteractor.execute(new OldBaseObserver<WorkerInfo>(workerView) {
            @Override
            public void onNext(WorkerInfo workerInfo) {
                infoModel = serviceInfoModelMapper.transformWorker(workerInfo);
                if (TextUtils.isEmpty(infoModel.getDistance()) || "0km".equals(infoModel.getDistance())) {
                    infoModel.setDistance(getDistance(new LatLng(infoModel.getLatitude(), infoModel.getLongitude())));
                }
                    workerView.render(infoModel);
            }
        });
    }

    private String getDistance(LatLng desc) {
        double distance = locationHelper.getDistance(desc) / 100;
        //四舍五入一位小数
        double lDistance = (double) Math.round(distance) / 10;
        return String.format(Locale.getDefault(), "距离%skm", lDistance);
    }

    public void like(String workerId, boolean like) {
        likeWorkerInteractor.setLike(like);
        likeWorkerInteractor.setWorkerId(workerId);
        likeWorkerInteractor.execute(new OldBaseObserver<Boolean>(workerView) {

            @Override
            public void showError(Throwable t) {
                if (t instanceof AuthException) {
                    workerView.showLogin();
                } else {
                    super.showError(t);
                }
            }

            @Override
            public void onNext(Boolean b) {
//                infoModel = serviceInfoModelMapper.transformWorker(workerInfo);
                workerView.likeWorker(infoModel);
            }
        });
    }

    public void collect(String workerId, final boolean collect) {
        collectWorkerInteractor.setCollect(collect);
        collectWorkerInteractor.setWorkerId(workerId);
        collectWorkerInteractor.execute(new OldBaseObserver<Boolean>(workerView) {
            @Override
            public void showError(Throwable t) {
                if (t instanceof AuthException) {
                    workerView.showLogin();
                } else {
                    super.showError(t);
                }
            }

            @Override
            public void onNext(Boolean b) {
//                infoModel = serviceInfoModelMapper.transformWorker(workerInfo);
                workerView.collectWorker(infoModel);
            }
        });
    }

    public void attemptCall() {
        // workerView.showLoading();
        callableInteractor.setWorkerId(infoModel.getId());
        final String phone = infoModel.getPhoneNumber();
        Log.d("phonenumber", "attemptCall: " + phone);
        callableInteractor.setPhoneNumber(phone);
        callableInteractor.execute(new OldBaseObserver<Boolean>(workerView) {
            @Override
            public void onError(Throwable e) {
                //    workerView.hideLoading();
                workerView.callIfEnabled(false, phone);
                workerView.showError(e);
            }

            @Override
            public void onNext(Boolean callable) {
                workerView.callIfEnabled(callable, phone);
            }
        });
    }


    private int pageIndex = 1;

    private static final int DEFAULT_SIZE = 10;

    /**
     * 获取工人评价列表
     */
    public void getWorkerEvaluations(String workerId) {
        pageIndex = 1;
        getWorkerEvaluationsInteractor.setWorkerId(workerId);
        getWorkerEvaluationsInteractor.setRange(pageIndex,DEFAULT_SIZE);
        getWorkerEvaluationsInteractor.execute(new OldBaseObserver<List<Evaluation>>(workerView) {
            @Override
            public void onNext(List<Evaluation> evaluations) {
                workerView.renderEvaluations(evaluations);
            }
        });
    }


    public void loadMoreEvaluations(){
        getWorkerEvaluationsInteractor.setRange(++pageIndex,DEFAULT_SIZE);
        getWorkerEvaluationsInteractor.execute(new OldBaseObserver<List<Evaluation>>(workerView) {
            @Override
            public void onNext(List<Evaluation> evaluations) {
                workerView.addMoreEvaluations(evaluations);
            }
        });
    }

    public void report(String id){
        reportWorkerInteractor.setWorkerId(id);
        reportWorkerInteractor.execute(new OldBaseObserver<String>(workerView) {
            @Override
            public void onNext(String s) {
                workerView.showMessage("举报成功");
            }
        });
    }


    public void share(){
        getShareInfoInteractor.execute(new OldBaseObserver<ShareInfo>(workerView) {
            @Override
            public void onNext(ShareInfo shareInfo) {
                workerView.share(shareInfo);
            }
        });
    }


    public void uploadShareInfo(){
        addShareRecordInteractor.execute(new OldBaseObserver<String>(workerView) {
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

    public WorkerCollectionEntity getWorkerInfoModel(){
        return infoModel;
    }

    public String getPhoneNumber() {
        return infoModel.getPhoneNumber();
    }

//    public List<Photo> getPhotos() {
//        return infoModel.getPhotos();
//    }

    public List<IServiceInfo.SystemCertification> getSystemCertificationList() {
        return infoModel.getSystemCertifications();
    }

    public void startDial() {
        EventBus.getDefault().post(new EventPhoneInfo(infoModel));
    }
}
