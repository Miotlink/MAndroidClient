package com.homepaas.sls.newmvp.presenter;

import com.homepaas.sls.data.network.dataformat.CollectedWorkerBody;
import com.homepaas.sls.httputils.RetrofitRequestCallBack;
import com.homepaas.sls.newmvp.base.IBasePresenter;
import com.homepaas.sls.newmvp.contract.ColleactionWorkerContract;
import com.homepaas.sls.newmvp.model.utils.ModelFactory;

/**
 * on 2017/6/4.
 */

public class ColleactionWorkerPresenter extends IBasePresenter<ColleactionWorkerContract.View> implements ColleactionWorkerContract.Presenter {

    private ColleactionWorkerContract.Model iModel = ModelFactory.getInstance().getColleactionWorkerModel();


    @Override
    public void dispose() {
        if (iModel != null) {
            iModel.dispose();
        }
    }


    @Override
    public void getListData() {
        iModel.getListData(mView, new RetrofitRequestCallBack<CollectedWorkerBody>() {
            @Override
            public void successRequest(CollectedWorkerBody data) {
                mView.initListData(data);
            }
        });
    }

    @Override
    public void cancelCollectWorker(String workId, boolean collect) {
        iModel.cancelCollectWorker(mView, workId, collect, new RetrofitRequestCallBack<String>() {
            @Override
            public void successRequest(String data) {
                mView.cancelCollectWorkerSuccess();
            }
        });
    }
}
