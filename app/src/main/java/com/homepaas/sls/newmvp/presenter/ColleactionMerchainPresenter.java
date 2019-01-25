package com.homepaas.sls.newmvp.presenter;

import com.homepaas.sls.data.network.dataformat.CollectedBusinessBody;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.IBasePresenter;
import com.homepaas.sls.newmvp.contract.ColleactionMerchantContract;
import com.homepaas.sls.newmvp.model.utils.ModelFactory;

/**
 * on 2017/6/4.
 */

public class ColleactionMerchainPresenter extends IBasePresenter<ColleactionMerchantContract.View> implements ColleactionMerchantContract.Presenter {

    private ColleactionMerchantContract.Model iModel = ModelFactory.getInstance().getColleactionMerchantModel();


    @Override
    public void dispose() {
        if (iModel != null) {
            iModel.dispose();
        }
    }


    @Override
    public void getListData() {
        iModel.getListData(mView, new RetrofitRequestCallBack<CollectedBusinessBody>() {
            @Override
            public void successRequest(CollectedBusinessBody data) {
                mView.initListData(data);
            }
        });
    }

    @Override
    public void cancelCollectWorker(String merchantId, boolean collect) {
        iModel.cancelCollectMerchant(mView, merchantId, collect, new RetrofitRequestCallBack<String>() {
            @Override
            public void successRequest(String data) {
                mView.cancelCollectBusinessSuccess();
            }
        });
    }
}
