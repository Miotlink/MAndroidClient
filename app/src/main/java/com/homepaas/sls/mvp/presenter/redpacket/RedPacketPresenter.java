package com.homepaas.sls.mvp.presenter.redpacket;
import com.homepaas.sls.domain.entity.RedPacketInfo;
import com.homepaas.sls.domain.interactor.GetMyRedPacketInteractor;
import com.homepaas.sls.httputils.OldBaseObserver;
import com.homepaas.sls.mvp.presenter.Presenter;
import com.homepaas.sls.mvp.view.redpacket.GetRedPacketView;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/4/28.
 */
public class RedPacketPresenter implements Presenter {


    GetMyRedPacketInteractor getMyRedPacketInteractor;
    GetRedPacketView getRedPacketView;

    public void setGetRedPacketView(GetRedPacketView getRedPacketView) {
        this.getRedPacketView = getRedPacketView;
    }

    @Inject
    public RedPacketPresenter(GetMyRedPacketInteractor getMyRedPacketInteractor) {
        this.getMyRedPacketInteractor = getMyRedPacketInteractor;
    }

    public void getMyRedPacket(String status){

        getRedPacketView.showLoading();
        getMyRedPacketInteractor.execute(new OldBaseObserver<RedPacketInfo>(getRedPacketView) {
            @Override
            public void onNext(RedPacketInfo redPacketInfo) {
               getRedPacketView.render(redPacketInfo.getPackets());
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
        getMyRedPacketInteractor.unsubscribe();
    }
}
