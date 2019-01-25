package com.homepaas.sls.mvp.presenter;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.ActivityNgInfoNew;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.DescriptionInfo;
import com.homepaas.sls.domain.entity.PriceEntity;
import com.homepaas.sls.domain.entity.ServiceScheduleEntity;
import com.homepaas.sls.domain.entity.SystemConfigEntity;
import com.homepaas.sls.domain.exception.AuthException;
import com.homepaas.sls.domain.interactor.AvatarsInteractor;
import com.homepaas.sls.domain.interactor.GetActivityInteractor;
import com.homepaas.sls.domain.interactor.GetServicePriceInteractor;
import com.homepaas.sls.domain.interactor.GetServiceScheduleInteractor;
import com.homepaas.sls.domain.interactor.GetSystemConfigInteractor;
import com.homepaas.sls.domain.param.PriceParam;
import com.homepaas.sls.domain.repository.DescriptionInfoRepo;
import com.homepaas.sls.domain.repository.ServiceRepo;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.httputils.RxUtil;
import com.homepaas.sls.mvp.view.AvatarView;
import com.homepaas.sls.mvp.view.GetDescriptionView;
import com.homepaas.sls.mvp.view.GetSystemConfigView;
import com.homepaas.sls.mvp.view.QtyView;
import com.homepaas.sls.mvp.view.ServiceView;
import com.homepaas.sls.mvp.view.order.ActivityView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by CJJ on 2016/9/14.
 */
@ActivityScope
public class ServicePresenter implements Presenter {


    @Inject
    GetServiceScheduleInteractor getServiceScheduleInteractor;
    @Inject
    GetServicePriceInteractor getServicePriceInteractor;
    @Inject
    GetActivityInteractor getActivityInteractor;
    @Inject
    AvatarsInteractor avatarsInteractor;
    @Inject
    GetSystemConfigInteractor getSystemConfigInteractor;
    private ServiceView serviceView;
    private ActivityView activityView;
    private AvatarView avatarView;
    private GetSystemConfigView configView;
    private QtyView qtyView;

    public void setQtyView(QtyView qtyView) {
        this.qtyView = qtyView;
    }

    public void setConfigView(GetSystemConfigView configView) {
        this.configView = configView;
    }

    public void setAvatarView(AvatarView avatarView) {
        this.avatarView = avatarView;
    }

    public void setActivityView(ActivityView activityView) {
        this.activityView = activityView;
    }

    public void setServiceView(ServiceView serviceView) {
        this.serviceView = serviceView;
    }

    @Inject
    public ServicePresenter() {
    }

    /**
     * 获取指定服务的价格
     *
     * @param serviceTypeId
     * @param providerType
     */
    public void getServicePrice(String serviceTypeId, String providerType,String serviceAddressId) {
        final PriceParam param = new PriceParam(serviceTypeId, providerType,serviceAddressId);
        getServicePriceInteractor.setParam(param);
        getServicePriceInteractor.execute(new OldBaseObserver<PriceEntity>(serviceView) {
            @Override
            public void onNext(PriceEntity priceEntity) {
                serviceView.onPriceResult(priceEntity);
            }
        });
    }

    /**
     * 获取服务的可以服务时间排班列表
     *
     * @param serviceTypeId
     */
    public void getSchedule(String serviceTypeId) {
        getServiceScheduleInteractor.setServiceTypeId(serviceTypeId);
        getServiceScheduleInteractor.execute(new OldBaseObserver<List<ServiceScheduleEntity>>(serviceView) {
            @Override
            public void onNext(List<ServiceScheduleEntity> schedules) {
                serviceView.onScheduleResult(schedules);
            }
        });
    }

    public void getAvailableActivity(String serviceTypeId) {
        getActivityInteractor.setServiceTypeId(serviceTypeId);
        getActivityInteractor.execute(new OldBaseObserver<ActivityNgInfoNew>(activityView) {
            @Override
            public void onNext(ActivityNgInfoNew actionEntity) {
                activityView.renderActivity(actionEntity);
            }
        });
    }

    /**
     * 获取工人头像
     *
     * @param id
     */
    public void getWorkerAvatar(String id) {
        avatarsInteractor.setId(id);
        avatarsInteractor.setType("1");
        avatarsInteractor.execute(new OldBaseObserver<AvatarsEntity>(avatarView) {
            @Override
            public void onNext(AvatarsEntity avatarsEntity) {
                avatarView.renderAvatar(avatarsEntity);
            }
        });
    }

    /**
     * 获取商户头像
     *
     * @param id
     */
    public void getBusinessAvatar(String id) {
        avatarsInteractor.setId(id);
        avatarsInteractor.setType("2");
        avatarsInteractor.execute(new OldBaseObserver<AvatarsEntity>(configView) {
            @Override
            public void onNext(AvatarsEntity avatarsEntity) {

            }

            @Override
            public void showError(Throwable t) {
//                super.showError(t);
            }
        });
    }

    /**
     * 获取全局的配置信息，比如地址标签、
     */
    public void getSystemConfig() {
        getSystemConfigInteractor.execute(new OldBaseObserver<SystemConfigEntity>(configView) {
            @Override
            public void showError(Throwable t) {
//                super.showError(t);
                if (t instanceof AuthException) {
                    configView.showLogin();
                }
            }

            @Override
            public void onNext(SystemConfigEntity systemConfigEntity) {
                configView.render(systemConfigEntity);
            }
        });
    }


    @Inject
    ServiceRepo serviceRepo;

    //获取小时工的服务数量列表
    public void getQty(String serviceTypeId) {
        serviceRepo.getQty(serviceTypeId)
                .compose(RxUtil.<List<String>>applySchedulers())
                .subscribe(new OldBaseObserver<List<String>>(qtyView) {
                    @Override
                    public void onNext(List<String> strings) {
                        qtyView.onQtyResult(strings);
                    }
                });
    }

    @Inject
    DescriptionInfoRepo descriptionInfoRepo;
    /**
     * 获取服务说明
     *
     * @param serviceTypeId
     */
    GetDescriptionView descriptionView;

    public void setDescriptionView(GetDescriptionView descriptionView) {
        this.descriptionView = descriptionView;
    }

    /**
     * 获取服务说明
     *
     * @param serviceTypeId
     */

    public void getServiceDescription(String serviceTypeId) {
        descriptionInfoRepo.getDescription("Code002", serviceTypeId)
                .compose(RxUtil.<DescriptionInfo>applySchedulers())
                .subscribe(new OldBaseObserver<DescriptionInfo>(descriptionView) {
                    @Override
                    public void onNext(DescriptionInfo descriptionInfo) {
                        if (descriptionInfo != null)
                            descriptionView.renderDescription(descriptionInfo.getDescription());
                    }
                });
    }


    /**
     * 获取温馨提示
     *
     * @param serviceTypeId
     */
    public void getWarmDescription(String serviceTypeId) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getServiceScheduleInteractor.unsubscribe();
        getServicePriceInteractor.unsubscribe();
        getActivityInteractor.unsubscribe();
        avatarsInteractor.unsubscribe();
        getSystemConfigInteractor.unsubscribe();
    }

}
