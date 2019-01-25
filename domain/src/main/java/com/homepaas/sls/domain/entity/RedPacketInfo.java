package com.homepaas.sls.domain.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
public class RedPacketInfo {

    List<RedPacket> packets;

    public List<RedPacket> getPackets() {
        return packets;
    }

    public void setPackets(List<RedPacket> packets) {
        this.packets = packets;
    }
}
