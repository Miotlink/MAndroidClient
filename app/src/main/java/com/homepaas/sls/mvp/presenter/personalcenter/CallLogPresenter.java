package com.homepaas.sls.mvp.presenter.personalcenter;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.CallLog;
import com.homepaas.sls.domain.interactor.CheckBusinessCallableInteractor;
import com.homepaas.sls.domain.interactor.CheckWorkerCallableInteractor;
import com.homepaas.sls.domain.interactor.DeleteCallLogsInteractor;
import com.homepaas.sls.domain.interactor.Interactor;
import com.homepaas.sls.event.EventCallState;
import com.homepaas.sls.event.EventPhoneInfo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.model.CallLogModel;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.mvp.model.mapper.CallLogModelMapper;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.personalcenter.CallLogView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * on 2016/1/28 0028
 *
 * @author zhudongjie .
 */
@ActivityScope
public class CallLogPresenter implements Presenter {

    private CallLogView callLogView;

    private Interactor getCallLogInteractor;

    private DeleteCallLogsInteractor deleteCallLogsInteractor;

    private CheckBusinessCallableInteractor checkBusinessCallableInteractor;

    private CheckWorkerCallableInteractor checkWorkerCallableInteractor;

    private CallLogModelMapper callLogModelMapper;

    private List<CallLogModel> callLogModels;

    private CallLogModel attemptCallModel;

    @Inject
    public CallLogPresenter(@Named("GetCallLogInteractor") Interactor getCallLogInteractor,
                            DeleteCallLogsInteractor deleteCallLogsInteractor, CheckBusinessCallableInteractor checkBusinessCallableInteractor, CheckWorkerCallableInteractor checkWorkerCallableInteractor, CallLogModelMapper callLogModelMapper) {
        this.getCallLogInteractor = getCallLogInteractor;
        this.deleteCallLogsInteractor = deleteCallLogsInteractor;
        this.checkBusinessCallableInteractor = checkBusinessCallableInteractor;
        this.checkWorkerCallableInteractor = checkWorkerCallableInteractor;
        this.callLogModelMapper = callLogModelMapper;
        EventBus.getDefault().register(this);
    }

    public void setCallLogView(CallLogView callLogView) {
        this.callLogView = callLogView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this);
        getCallLogInteractor.unsubscribe();
        deleteCallLogsInteractor.unsubscribe();
        checkWorkerCallableInteractor.unsubscribe();
    }


    public void getCallLogs() {
//        callLogView.showLoading();
        getCallLogInteractor.execute(new OldBaseObserver<List<CallLog>>(callLogView) {
            @Override
            public void onNext(List<CallLog> callLogs) {
                callLogModels = callLogModelMapper.transform(callLogs);
                callLogView.render(callLogModels);
            }
        });
    }


    public void attemptCall(final int position) {
        attemptCallModel = callLogModels.get(position);
        if (attemptCallModel.getType() == IServiceInfo.TYPE_WORKER) {
            checkWorkerCallableInteractor.setPhoneNumber(attemptCallModel.getPhoneNumber());
            checkWorkerCallableInteractor.setWorkerId(attemptCallModel.getId());
            checkWorkerCallableInteractor.execute(new OldBaseObserver<Boolean>(callLogView) {
                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    callLogView.disableCall(position);
                }

                @Override
                public void onNext(Boolean aBoolean) {
                    if (aBoolean) {
                        callLogView.dial(attemptCallModel.getPhoneNumber());
                    } else {
                        callLogView.disableCall(position);
                    }
                }
            });
        } else {
            checkBusinessCallableInteractor.setPhoneNumber(attemptCallModel.getPhoneNumber());
            checkBusinessCallableInteractor.setBusinessId(attemptCallModel.getId());
            checkBusinessCallableInteractor.execute(new OldBaseObserver<Boolean>(callLogView) {
                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                    callLogView.disableCall(position);
                }

                @Override
                public void onNext(Boolean aBoolean) {
                    if (aBoolean) {
                        callLogView.dial(attemptCallModel.getPhoneNumber());
                    } else {
                        callLogView.disableCall(position);
                    }
                }
            });
        }
    }


    public void deleteCallLog(CallLogModel logModel, final int position) {
        deleteCallLogsInteractor.setPhoneNumber(logModel.getPhoneNumber());
        callLogView.showLoading();
        deleteCallLogsInteractor.execute(new OldBaseObserver<String>(callLogView) {
            @Override
            public void onNext(String s) {
                callLogModels.remove(position);
                callLogView.delete(position);
            }
        });
    }

    public CallLogModel getAttemptCall() {
        return attemptCallModel;
    }

    public void startDial() {
        EventBus.getDefault().post(new EventPhoneInfo(attemptCallModel));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCallLogSaved(EventCallState state) {
        getCallLogs();
    }
}
