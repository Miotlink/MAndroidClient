package com.homepaas.sls.mvp.view;

import com.homepaas.sls.domain.entity.BannerInfo;
import com.homepaas.sls.domain.entity.CheckIsReceivedRedCoupsEntry;
import com.homepaas.sls.domain.entity.IService;
import com.homepaas.sls.mvp.model.IServiceInfo;
import com.homepaas.sls.mvp.model.WorkerBussinesModel;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * on 2016/1/14 0014
 *
 * @author zhudongjie .
 */
public interface MainContentView extends BaseView,UpdateView{

    void showService(List<IServiceInfo> serviceInfoList);
    void call(WorkerBussinesModel workerBussinesModel,boolean callAble);
    void call(IService service, boolean enable);
    void commit(String msg);
    void renderReceivedRedCoups(CheckIsReceivedRedCoupsEntry checkIsReceivedRedCoupsEntry);
}
