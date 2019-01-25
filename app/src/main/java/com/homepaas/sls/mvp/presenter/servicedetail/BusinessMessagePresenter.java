package com.homepaas.sls.mvp.presenter.servicedetail;

import com.homepaas.sls.di.scope.ActivityScope;
import com.homepaas.sls.domain.entity.AvatarsEntity;
import com.homepaas.sls.domain.entity.BusinessInfo;
import com.homepaas.sls.domain.entity.GetBusinessTagsInfo;
import com.homepaas.sls.domain.interactor.AvatarsInteractor;
import com.homepaas.sls.domain.interactor.GetBusinessInfoInteractor;
import com.homepaas.sls.domain.interactor.GetBusinessTagsInfoInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.model.BusinessInfoModel;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.mvp.model.mapper.ServiceInfoModelMapper;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.servicedetail.BusinessMessageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * on 2016/1/20 0020
 *
 * @author zhudongjie .
 */
@ActivityScope
public class BusinessMessagePresenter implements Presenter {

    private static final String TAG = "BusinessMessagePresenter";

    private ServiceInfoModelMapper serviceInfoModelMapper;

    private GetBusinessInfoInteractor getBusinessInfoInteractor;

    private BusinessMessageView businessMessageView;

    private GetBusinessTagsInfoInteractor getBusinessTagsInfoInteractor;

    private BusinessInfoModel infoModel;
    private AvatarsInteractor avatarsInteractor;

    @Inject
    public BusinessMessagePresenter(ServiceInfoModelMapper serviceInfoModelMapper, GetBusinessInfoInteractor getBusinessInfoInteractor
            ,GetBusinessTagsInfoInteractor getBusinessTagsInfoInteractor,AvatarsInteractor avatarsInteractor) {
        this.serviceInfoModelMapper = serviceInfoModelMapper;
        this.getBusinessInfoInteractor = getBusinessInfoInteractor;
        this.getBusinessTagsInfoInteractor = getBusinessTagsInfoInteractor;
        this.avatarsInteractor = avatarsInteractor;
    }

    public void setView(BusinessMessageView view){
        businessMessageView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        avatarsInteractor.unsubscribe();
        getBusinessInfoInteractor.unsubscribe();
        getBusinessTagsInfoInteractor.unsubscribe();
//        Log.d(TAG, "destroy: getBusinessInfoInteractor.unsubscribe()");
    }
    public void getBusinessAvatars(String id){
        avatarsInteractor.setType("2");
        avatarsInteractor.setId(id);
        avatarsInteractor.execute(new OldBaseObserver<AvatarsEntity>(businessMessageView) {
          @Override
            public void onNext(AvatarsEntity avatarsEntity) {
                businessMessageView.renderAvatars(avatarsEntity);
            }
        });
    }
    public void getBusinessTagsInfo(final String businessId){
        getBusinessTagsInfoInteractor.setBusinessId(businessId);
        getBusinessTagsInfoInteractor.execute(new OldBaseObserver<GetBusinessTagsInfo>(businessMessageView) {
              @Override
            public void onNext(GetBusinessTagsInfo getBusinessTagsInfo) {
                businessMessageView.render(getBusinessTagsInfo);
            }
        });
    }
    public void getBusinessInfo(String businessId){
        businessMessageView.showLoading();
        getBusinessInfoInteractor.setBusinessId(businessId);
        getBusinessInfoInteractor.execute(new OldBaseObserver<BusinessInfo>(businessMessageView) {
            @Override
            public void onNext(BusinessInfo businessInfo) {
//                Log.d(TAG, "onNext: ");
                infoModel = serviceInfoModelMapper.transformBusiness(businessInfo);
                businessMessageView.render(infoModel);
            }

        });
    }

    public List<IServiceInfo.SystemCertification> getSystemCertificationList() {
        return infoModel.getSystemCertifications();
    }

    public List<String> getLicenseHqic(){
        List<String> photos = new ArrayList<>();
        List<IServiceInfo.Photo> photoList = infoModel.getLicencePhotos();
        if (photoList != null && !photoList.isEmpty()){
            for (IServiceInfo.Photo photo : photoList){
                photos.add(photo.getHqPic());
            }
        }
        return photos;
    }

}
