package com.homepaas.sls.newmvp.presenter;

import com.homepaas.sls.newmvp.base.IBasePresenter;
import com.homepaas.sls.newmvp.contract.ServiceMerchantContract;

/**
 * Created by Administrator on 2017/3/31.
 */

public class ServiceMerchantPresenter extends IBasePresenter<ServiceMerchantContract.View> implements ServiceMerchantContract.Presenter {


    @Override
    public void loadPersonalInfo() {
//            getPersonalInfoInteractor.execute(new OldBaseObserver<PersonalInfo>(detailServiceWebView) {
//                @Override
//                public void onNext(PersonalInfo personalInfo) {
//                    在登录的情况下获取个人信息，并保存telphonenumber,不需要返回给上层，已经在repo层保存
//                    detailServiceWebView.pushTelephone(personalInfo.getPhoneNumber());
//                }
//            });
    }

    @Override
    public void dispose() {

    }
}
