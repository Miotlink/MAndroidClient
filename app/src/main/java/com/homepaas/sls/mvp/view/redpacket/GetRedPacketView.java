package com.homepaas.sls.mvp.view.redpacket;

import com.homepaas.sls.domain.entity.RedPacket;
import com.homepaas.sls.newmvp.base.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
public interface GetRedPacketView extends BaseView {

    void render(List<RedPacket> packets);
}
