package com.homepaas.sls.data.network.dataformat;

import com.google.gson.annotations.SerializedName;
import com.homepaas.sls.domain.entity.RedPacket;

import java.util.List;

/**
 * Created by Administrator on 2016/4/28.
 */
public class MyRedPacketBody {

    @SerializedName("RedEnvelopeList")
    List<RedPacket> packets;

    public MyRedPacketBody() {
    }

    public MyRedPacketBody(List<RedPacket> packets) {
        this.packets = packets;
    }

    public List<RedPacket> getPackets() {
        return packets;
    }

    public void setPackets(List<RedPacket> packets) {
        this.packets = packets;
    }
}
